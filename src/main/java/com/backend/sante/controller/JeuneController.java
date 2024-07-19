package com.backend.sante.controller;


import com.backend.sante.dto.JeuneDto;
import com.backend.sante.entities.*;
import com.backend.sante.exceptions.EmailNonValideException;
import com.backend.sante.exceptions.JeuneException;
import com.backend.sante.exceptions.JeuneNotFoundException;
import com.backend.sante.exceptions.PhoneNonValideException;
import com.backend.sante.services.JeuneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.logging.Logger;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class JeuneController {
    private static final Logger logger = Logger.getLogger(JeuneController.class.getName());
    private JeuneService jeuneService;

    @GetMapping("/jeunes/{id}")
    public ResponseEntity<?> getJeuneById(@PathVariable(value = "id") Long id) {
        try {
            Object jeune = jeuneService.getJeuneById(id);
            return ResponseEntity.ok().body(jeune);
        } catch (JeuneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/register/jeunes/scolarise")
    public ResponseEntity<JeuneDto> saveJeuneScolarise(@RequestBody JeuneScolarise jeuneScolarise) throws EmailNonValideException,PhoneNonValideException{

        JeuneDto savedJeune = jeuneService.saveJeune(jeuneScolarise);
        return ResponseEntity.ok(savedJeune);

    }

    @PostMapping("/register/jeunes/nonscolarise")
    public ResponseEntity<JeuneDto> saveJeuneNonScolarise(@RequestBody JeuneNonScolarise jeuneNonScolarise) {
        try {
            JeuneDto savedJeune = jeuneService.saveJeune(jeuneNonScolarise);
            return ResponseEntity.ok(savedJeune);
        } catch (EmailNonValideException | PhoneNonValideException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/jeunes/{jeuneId}/antecedents/familiaux")
    public ResponseEntity<?> addAntecedentFamilial(@PathVariable Long jeuneId, @RequestBody AntecedentFamilial antecedentFamilial) {
        try {
            logger.info("Received request to add antecedent familial for jeuneId: " + jeuneId);
            AntecedentFamilial savedAntecedentFamilial = jeuneService.addAntecedentFamilial(jeuneId, antecedentFamilial);
            logger.info("Successfully added antecedent familial for jeuneId: " + jeuneId);
            return ResponseEntity.ok(savedAntecedentFamilial);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to add antecedent familial: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/jeunes/{jeuneId}/antecedents/personnels")
    public ResponseEntity<?> addAntecedentPersonnel(@PathVariable Long jeuneId, @RequestBody AntecedentPersonnel antecedentPersonnel) {
         try {
                 logger.info("Received request to add antecedent personnel for jeuneId: " + jeuneId);
                  AntecedentPersonnel savedAntecedentPersonnel = jeuneService.addAntecedentPersonnel(jeuneId, antecedentPersonnel);
                  logger.info("Successfully added antecedent personnel for jeuneId: " + jeuneId);
                  return ResponseEntity.ok(savedAntecedentPersonnel);
         } catch (IllegalArgumentException e) {
                    logger.warning("Failed to add antecedent personnel: " + e.getMessage());
                    return ResponseEntity.badRequest().body(e.getMessage());
         }
    }
    @GetMapping("/jeunes/{jeuneId}/antecedents")
    public ResponseEntity<?> getAntecedents(@PathVariable Long jeuneId) {
        try {
            logger.info("Received request to get antecedents for jeuneId: " + jeuneId);
            Map<String, Object> result = jeuneService.getAntecedents(jeuneId);
            logger.info("Successfully retrieved antecedents for jeuneId: " + jeuneId);
            return ResponseEntity.ok(result);
        } catch (JeuneException e) {
            logger.warning("Failed to retrieve antecedents: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/register/jeunes/confirmation")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        Jeune jeune = jeuneService.confirmEmail(token);
        return ResponseEntity.ok("Email confirmed successfully");
    }

}