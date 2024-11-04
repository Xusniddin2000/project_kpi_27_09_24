package com.example.project_kpi_27_09_24.config;


import com.example.project_kpi_27_09_24.entity.auth.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication   authentication= SecurityContextHolder.getContext().getAuthentication();
        if( authentication ==null ||  !authentication.isAuthenticated()  ||
             authentication instanceof AnonymousAuthenticationToken){
            return Optional.of(Long.valueOf("1"));
        }

        User userPrincipal= (User) authentication.getPrincipal();

        return     Optional.of(userPrincipal.getId());

    }
}
