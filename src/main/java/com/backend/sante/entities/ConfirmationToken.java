package com.backend.sante.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Jeune.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "jeune_id")
    private Jeune jeune;

    private Date createdDate;

    public ConfirmationToken(String token, Jeune jeune) {
        this.token = token;
        this.jeune = jeune;
        this.createdDate = new Date();
    }
}
