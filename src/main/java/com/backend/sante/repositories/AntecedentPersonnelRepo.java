package com.backend.sante.repositories;



import com.backend.sante.entities.AntecedentPersonnel;
import com.backend.sante.entities.Jeune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AntecedentPersonnelRepo extends JpaRepository<AntecedentPersonnel,Long> {
    Optional<AntecedentPersonnel> findByJeune(Jeune jeune);
}
