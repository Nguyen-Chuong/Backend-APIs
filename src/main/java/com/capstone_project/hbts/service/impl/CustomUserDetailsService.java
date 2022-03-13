package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Provider;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.ProviderRepository;
import com.capstone_project.hbts.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ProviderRepository providerRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    ProviderRepository providerRepository) {
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Request to load user by username");
        // this method get credential from db and authenticationManager compare to credential that user sent
        // in its authenticate method
        if(username.startsWith("u-")){
            Users user = userRepository.getUsersByUsername(username);
            if(user == null){
                throw new UsernameNotFoundException("can't find account");
            }
            // if use pre-authorize via role -> config has role (role name) in format ROLE_NAME
            // if use pre-authorize via authority -> create new table authority many to one role (rcm)
            // or simply add new authority by role name
            Set<GrantedAuthority> grantedAuthoritySet = user.getListRole()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
        } else {
            Provider provider = providerRepository.getProviderByUsername(username);
            if (provider == null) {
                throw new UsernameNotFoundException("can't find account");
            }
            return new User(provider.getUsername(), provider.getPassword(), new ArrayList<>());
        }
    }

}
