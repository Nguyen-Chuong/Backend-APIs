package com.capstone_project.hbts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class EntityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if authentication from context is null or unauthenticated
        if(authentication == null || !authentication.isAuthenticated()){
            return Optional.empty();
        }else {
            return Optional.of(((User)authentication.getPrincipal()).getUsername());
        }
    }

}
