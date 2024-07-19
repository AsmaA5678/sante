package com.backend.sante.entities;

import com.backend.sante.enums.Sexe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 6, discriminatorType = DiscriminatorType.STRING)
@Data
@AllArgsConstructor @NoArgsConstructor
public class Jeune{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private InfoUser infoUser;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    private Date dateNaissance;
    private int age;
    @Column(unique = true)
    private int identifiantPatient;
    private boolean scolarise;
    @Column(unique = true)
    private String cin;
    private Boolean isConfirmed = false;
    private Boolean isFirstAuth = true;

    private String ROLE="JEUNE";

    @OneToOne(mappedBy = "jeune" , cascade = CascadeType.ALL)
    private AntecedentFamilial antecedentFamilial;

    @OneToOne(mappedBy = "jeune" , cascade = CascadeType.ALL)
    private AntecedentPersonnel antecedentPersonnel;
}
