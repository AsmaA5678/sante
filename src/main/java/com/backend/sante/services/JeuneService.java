package com.backend.sante.services;


import com.backend.sante.dto.JeuneDto;
import com.backend.sante.entities.AntecedentFamilial;
import com.backend.sante.entities.AntecedentPersonnel;
import com.backend.sante.entities.Jeune;
import com.backend.sante.exceptions.EmailNonValideException;
import com.backend.sante.exceptions.JeuneException;
import com.backend.sante.exceptions.JeuneNotFoundException;
import com.backend.sante.exceptions.PhoneNonValideException;

import java.util.Map;


public interface JeuneService extends MailService {
    JeuneDto saveJeune(Jeune jeune) throws EmailNonValideException, PhoneNonValideException;
    AntecedentFamilial addAntecedentFamilial(Long jeuneId, AntecedentFamilial antecedentFamilial);
    AntecedentPersonnel addAntecedentPersonnel(Long jeuneId, AntecedentPersonnel antecedentPersonnel);
    Map<String, Object> getAntecedents(Long jeuneId) throws JeuneException;
    Object getJeuneById(Long id) throws JeuneNotFoundException;
}