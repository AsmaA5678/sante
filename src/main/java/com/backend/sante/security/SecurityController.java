package com.backend.sante.security;

import com.backend.sante.entities.Jeune;
import com.backend.sante.exceptions.UserNameNotFoundException;
import com.backend.sante.repositories.JeuneRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth/login")
@AllArgsConstructor
public class SecurityController {

    private static final Logger logger = Logger.getLogger(SecurityController.class.getName());

    private final JwtEncoder jwtEncoder;
    private final JeuneRepo jeuneRepo;
    private final AuthenticationManager authenticationManagerJeune;

    @PostMapping("/jeunes")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) throws UserNameNotFoundException {
        String username = loginData.get("username");
        String password = loginData.get("password");

        logger.info("Login attempt for username: " + username);

        try {
            Authentication authentication = authenticateUser(username, password);
            Jeune jeune = getJeuneByUsername(username);

            logger.info("Login successful for username: " + username);

            return generateToken(authentication, jeune);

        } catch (BadCredentialsException e) {
            logger.severe("Invalid credentials for username: " + username);
            throw new BadCredentialsException("Invalid username or password");
        } catch (AuthenticationException e) {
            logger.severe("Authentication failed for username: " + username + " due to: " + e.getMessage());
            throw new RuntimeException("Authentication failed", e);
        }
    }

    private Authentication authenticateUser(String username, String password) {
        return authenticationManagerJeune.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }

    private Jeune getJeuneByUsername(String username) throws UserNameNotFoundException {
        return jeuneRepo.findJeuneByMailOrCinOrCNEOrCodeMASSAR(username)
                .orElseThrow(() -> new UserNameNotFoundException("User not found with username: " + username));
    }

    private Map<String, String> generateToken(Authentication authentication, Jeune jeune) {
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .subject(authentication.getName())
                .claim("username", jeune.getInfoUser().getMail())
                .claim("role", scope)
                .claim("id", String.valueOf(jeune.getId()))
                .claim("nom", jeune.getInfoUser().getNom())
                .claim("prenom", jeune.getInfoUser().getPrenom())
                .claim("mail", jeune.getInfoUser().getMail())
                .claim("confirmed", String.valueOf(jeune.getIsConfirmed()))
                .claim("isFirstAuth", String.valueOf(jeune.getIsFirstAuth()))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(10, ChronoUnit.MINUTES))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );

        String token = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}