package com.backend.sante.mappers;


import com.backend.sante.dto.JeuneNonScolariseDto;
import com.backend.sante.entities.InfoUser;
import com.backend.sante.entities.JeuneNonScolarise;
import org.springframework.stereotype.Service;

@Service
public class JeuneNonScolariseMapper {

    public JeuneNonScolariseDto toDtoJeuneNS(JeuneNonScolarise jeuneNonScolarise) {
        JeuneNonScolariseDto jeuneNonScolariseDto = new JeuneNonScolariseDto();
        InfoUser infoUser = jeuneNonScolarise.getInfoUser();

        jeuneNonScolariseDto.setId(jeuneNonScolarise.getId());
        jeuneNonScolariseDto.setPrenom(infoUser.getPrenom());
        jeuneNonScolariseDto.setNom(infoUser.getNom());
        jeuneNonScolariseDto.setMail(infoUser.getMail());
        jeuneNonScolariseDto.setNumTele(infoUser.getNumTele());
        jeuneNonScolariseDto.setSexe(jeuneNonScolarise.getSexe());
        jeuneNonScolariseDto.setDateNaissance(jeuneNonScolarise.getDateNaissance());
        jeuneNonScolariseDto.setAge(jeuneNonScolarise.getAge());
        jeuneNonScolariseDto.setIdentifiantPatient(jeuneNonScolarise.getIdentifiantPatient());
        jeuneNonScolariseDto.setCin(jeuneNonScolarise.getCin());
        jeuneNonScolariseDto.setConfirmed(jeuneNonScolarise.getIsConfirmed());
        jeuneNonScolariseDto.setFirstAuth(jeuneNonScolarise.getIsFirstAuth());
        jeuneNonScolariseDto.setROLE(jeuneNonScolarise.getROLE());
        jeuneNonScolariseDto.setDernierNiveauEtudes(jeuneNonScolarise.getDernierNiveauEtudes());
        jeuneNonScolariseDto.setSituationActuelle(jeuneNonScolarise.getSituationActuelle());

        return jeuneNonScolariseDto;
    }
}

