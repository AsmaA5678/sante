package com.backend.sante.mappers;



import com.backend.sante.dto.JeuneDto;
import com.backend.sante.entities.Jeune;
import org.springframework.stereotype.Service;

@Service
public class JeuneMapper {
    public static JeuneDto toDtoJeune(Jeune jeune) {
        JeuneDto dto = new JeuneDto();
        dto.setId(jeune.getId());
        dto.setNom(jeune.getInfoUser().getNom());
        dto.setPrenom(jeune.getInfoUser().getPrenom());
        dto.setMail(jeune.getInfoUser().getMail());
        dto.setNumTele(jeune.getInfoUser().getNumTele());
        dto.setSexe(jeune.getSexe());
        dto.setDateNaissance(jeune.getDateNaissance());
        dto.setAge(jeune.getAge());
        dto.setIdentifiantPatient(jeune.getIdentifiantPatient());
        dto.setScolarise(jeune.isScolarise());
        dto.setCin(jeune.getCin());
        dto.setIsConfirmed(jeune.getIsConfirmed());
        dto.setIsFirstAuth(jeune.getIsFirstAuth());
        dto.setRole(jeune.getROLE());
        return dto;
    }
}
