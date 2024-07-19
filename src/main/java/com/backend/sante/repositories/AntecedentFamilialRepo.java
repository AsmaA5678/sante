package com.backend.sante.repositories;




import com.backend.sante.entities.AntecedentFamilial;
import com.backend.sante.entities.Jeune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AntecedentFamilialRepo extends JpaRepository<AntecedentFamilial,Long> {
    Optional<AntecedentFamilial> findByJeune(Jeune jeune);
}
