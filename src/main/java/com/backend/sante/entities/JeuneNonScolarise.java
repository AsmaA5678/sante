package com.backend.sante.entities;


import com.backend.sante.enums.NiveauEtudes;
import com.backend.sante.enums.Situation;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("NONSC")
@Data @AllArgsConstructor @NoArgsConstructor
public class JeuneNonScolarise extends Jeune {
    @Enumerated(EnumType.STRING)
    private NiveauEtudes dernierNiveauEtudes;
    @Enumerated(EnumType.STRING)
    private Situation situationActuelle;
}

