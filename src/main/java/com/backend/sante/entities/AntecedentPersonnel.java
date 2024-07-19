package com.backend.sante.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AntecedentPersonnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "jeune_id")
    private Jeune jeune;

    @ElementCollection
    private List<String> medicaux;

    private Boolean chirurgicaux;

    @ElementCollection
    private List<String> habitudes;
}
