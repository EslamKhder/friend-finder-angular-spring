package com.spring.management.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.spring.management.model.organization.Organization;
import com.spring.management.repository.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private OrganizationRepository organizationRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(OrganizationRepository organizationRepository,
                    PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
                    throws AuthenticationException {
        String referenceId = authentication.getName(); // true
        String password = authentication.getCredentials().toString();
        Optional<Organization> organization =
                        organizationRepository.findByReferenceId(referenceId);

        if (organization.isPresent()) {
            if (passwordEncoder.matches(password, organization.get().getPassword())) {

                List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                        (organization.get().getRoles() != null) ?
                                organization.get().getRoles().stream()
                                        .map(role -> new SimpleGrantedAuthority(role.getRole().getCode()))
                                        .collect(Collectors.toList())
                                : Collections.emptyList();
                return new UsernamePasswordAuthenticationToken(referenceId, password,
                        simpleGrantedAuthorities);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
