package com.backend.sante.entities;

import com.backend.sante.enums.NiveauEtudes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SC")
@Data @AllArgsConstructor @NoArgsConstructor
public class JeuneScolarise extends Jeune{
    @Enumerated(EnumType.STRING)
    private NiveauEtudes niveauEtudesActuel;
    @Column(unique = true)
    private String CNE;
    @Column(unique = true)
    private String codeMASSAR;
}
