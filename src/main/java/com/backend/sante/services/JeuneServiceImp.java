package com.backend.sante.services;

import com.backend.sante.dto.JeuneDto;
import com.backend.sante.entities.*;
import com.backend.sante.exceptions.*;
import com.backend.sante.mappers.JeuneMapper;
import com.backend.sante.mappers.JeuneNonScolariseMapper;
import com.backend.sante.mappers.JeuneScolariseMapper;
import com.backend.sante.repositories.AntecedentFamilialRepo;
import com.backend.sante.repositories.AntecedentPersonnelRepo;
import com.backend.sante.repositories.ConfirmationTokenRepo;
import com.backend.sante.repositories.JeuneRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class JeuneServiceImp implements JeuneService {

    private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;

    private final JeuneMapper jeuneMapper;
    private final JeuneNonScolariseMapper jeuneNonScolariseMapper;
    private final JeuneScolariseMapper jeuneScolariseMapper;

    private final Validator validator;
    private final JeuneRepo jeuneRepo;

    private final AntecedentFamilialRepo antecedentFamilialRepo;
    private final AntecedentPersonnelRepo antecedentPersonnelRepo;

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenRepo confirmationTokenRepo;

    @Override
    public JeuneDto saveJeune(Jeune jeune) throws EmailNonValideException, PhoneNonValideException {
        validateJeuneInfo(jeune);

        jeune.getInfoUser().setPassword(passwordEncoder.encode(jeune.getInfoUser().getPassword()));
        jeune.setAge(calculateAge(jeune.getDateNaissance()));
        jeune.setIdentifiantPatient(generateIdentifiantPatient());

        Jeune savedJeune = jeuneRepo.save(jeune);
        sendConfirmationEmailAsync(savedJeune);

        return jeuneMapper.toDtoJeune(savedJeune);
    }

    private void validateJeuneInfo(Jeune jeune) throws EmailNonValideException, PhoneNonValideException  {
        if (!validator.isValidEmail(jeune.getInfoUser().getMail())) {
            throw new EmailNonValideException("Invalid email format");
        }
        if (!validator.isValidPhoneNumber(jeune.getInfoUser().getNumTele())) {
            throw new PhoneNonValideException("Invalid phone number format");
        }
//        if (jeune.getAge() >= 16 && !isValidCIN(jeune.getCin())) {
//            throw new CinNonValideException("Invalid CIN format");
//        }
    }

    private int calculateAge(Date birthDate) {
        LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(localBirthDate, LocalDate.now()).getYears();
    }

    private int generateIdentifiantPatient() {
        return new Random().nextInt(900000) + 100000;
    }

    private void sendConfirmationEmailAsync(Jeune jeune) {
        String token = UUID.randomUUID().toString();
        confirmationTokenRepo.save(new ConfirmationToken(token, jeune));
        new Thread(() -> sendConfirmationEmail(jeune.getInfoUser().getMail(), token)).start();
    }

    @Override
    public AntecedentFamilial addAntecedentFamilial(Long jeuneId, AntecedentFamilial antecedentFamilial) {
        jeuneRepo.findById(jeuneId)
                .ifPresentOrElse(jeune -> {
                            antecedentFamilial.setJeune(jeune);
                            antecedentFamilialRepo.save(antecedentFamilial);
                        },
                        () -> {
                            throw new IllegalArgumentException("Jeune not found");
                        });
        return antecedentFamilial;
    }

    @Override
    public AntecedentPersonnel addAntecedentPersonnel(Long jeuneId, AntecedentPersonnel antecedentPersonnel) {
        jeuneRepo.findById(jeuneId)
                .ifPresentOrElse(jeune -> {
                            antecedentPersonnel.setJeune(jeune);
                            antecedentPersonnelRepo.save(antecedentPersonnel);
                        },
                        () -> {
                            throw new IllegalArgumentException("Jeune not found");
                        });
        return antecedentPersonnel;
    }


    @Override
    public Map<String, Object> getAntecedents(Long jeuneId) throws JeuneException {
        Jeune jeune = jeuneRepo.findById(jeuneId)
                .orElseThrow(() -> new JeuneException("Jeune n'existe pas"));

        Map<String, Object> result = new HashMap<>();
        result.put("AntecedentFamilial", antecedentFamilialRepo.findByJeune(jeune).orElse(null));
        result.put("AntecedentPersonnel", antecedentPersonnelRepo.findByJeune(jeune).orElse(null));

        return result;
    }

    @Override
    public Object getJeuneById(Long id) throws JeuneNotFoundException {
        Jeune jeune = jeuneRepo.findById(id)
                .orElseThrow(() -> new JeuneNotFoundException("Jeune not found for this id :: " + id));

        if (jeune instanceof JeuneScolarise) {
            return jeuneScolariseMapper.toDtoJeuneS((JeuneScolarise) jeune);
        } else if (jeune instanceof JeuneNonScolarise) {
            return jeuneNonScolariseMapper.toDtoJeuneNS((JeuneNonScolarise) jeune);
        } else {
            return jeuneMapper.toDtoJeune(jeune);
        }
    }

    @Override
    public void sendEmail(String to, String subject, String htmlBody) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Jeune confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid confirmation token"));

        if (new Date().getTime() - confirmationToken.getCreatedDate().getTime() > EXPIRATION_TIME_MS) {
            throw new RuntimeException("Confirmation token has expired");
        }

        Jeune jeune = confirmationToken.getJeune();
        jeune.setIsConfirmed(true);
        jeuneRepo.save(jeune);
        return jeune;
    }

    @Override
    public void sendConfirmationEmail(String recipientEmail, String confirmationToken) {
        String confirmationUrl = "http://localhost:8080/register/jeunes/confirmation?token=" + confirmationToken;
        String subject = "Confirm Your Email Address";
        String emailContent = "<p>Dear User,</p>"
                + "<p>Please click the link below to confirm your email address:</p>"
                + "<p><a href=\"" + confirmationUrl + "\">Confirm Email</a></p>"
                + "<p>Thank you!</p>";

        sendEmail(recipientEmail, subject, emailContent);
    }
}
