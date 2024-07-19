package com.backend.sante.security;

import com.backend.sante.entities.Jeune;
import com.backend.sante.repositories.JeuneRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class InfoUserService implements UserDetailsService {
    private final JeuneRepo jeuneRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Jeune jeune = jeuneRepo.findJeuneByMailOrCinOrCNEOrCodeMASSAR(username)
                .orElseThrow(() -> new UsernameNotFoundException("Jeune not found with username: " + username));

        return User.withUsername(username)
                .password(jeune.getInfoUser().getPassword())
                .roles(jeune.getROLE())
                .build();
    }
}
