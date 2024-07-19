package com.backend.sante.dto;

import com.backend.sante.enums.Sexe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JeuneDto {
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String numTele;
    private Sexe sexe;
    private Date dateNaissance;
    private int age;
    private int identifiantPatient;
    private boolean scolarise;
    private String cin;
    private Boolean isConfirmed;
    private Boolean isFirstAuth;
    private String role;
}