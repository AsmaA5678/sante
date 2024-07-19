package com.backend.sante.mappers;



import com.backend.sante.dto.JeuneScolariseDto;
import com.backend.sante.entities.InfoUser;
import com.backend.sante.entities.JeuneScolarise;
import org.springframework.stereotype.Service;

@Service
public class JeuneScolariseMapper {
    public JeuneScolariseDto toDtoJeuneS(JeuneScolarise jeuneScolarise) {
        JeuneScolariseDto jeuneScolariseDto = new JeuneScolariseDto();
        InfoUser infoUser = jeuneScolarise.getInfoUser();
        jeuneScolariseDto.setId(jeuneScolarise.getId());
        jeuneScolariseDto.setPrenom(infoUser.getPrenom());
        jeuneScolariseDto.setNom(infoUser.getNom());
        jeuneScolariseDto.setMail(infoUser.getMail());
        jeuneScolariseDto.setNumTele(infoUser.getNumTele());
        jeuneScolariseDto.setSexe(jeuneScolarise.getSexe());
        jeuneScolariseDto.setDateNaissance(jeuneScolarise.getDateNaissance());
        jeuneScolariseDto.setAge(jeuneScolarise.getAge());
        jeuneScolariseDto.setIdentifiantPatient(jeuneScolarise.getIdentifiantPatient());
        jeuneScolariseDto.setCin(jeuneScolarise.getCin());
        jeuneScolariseDto.setConfirmed(jeuneScolarise.getIsConfirmed());
        jeuneScolariseDto.setFirstAuth(jeuneScolarise.getIsFirstAuth());
        jeuneScolariseDto.setROLE(jeuneScolarise.getROLE());
        jeuneScolariseDto.setNiveauEtudesActuel(jeuneScolarise.getNiveauEtudesActuel());
        jeuneScolariseDto.setCNE(jeuneScolarise.getCNE());
        jeuneScolariseDto.setCodeMASSAR(jeuneScolarise.getCodeMASSAR());

        return jeuneScolariseDto;
    }
}

