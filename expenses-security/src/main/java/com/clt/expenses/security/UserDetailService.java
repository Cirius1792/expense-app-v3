package com.clt.expenses.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailService implements ReactiveUserDetailsService {

    private final ApplicationCredentialManager applicationCredentialManager;

    public UserDetailService(ApplicationCredentialManager applicationCredentialManager) {
        this.applicationCredentialManager = applicationCredentialManager;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return applicationCredentialManager
                .retrieve(username)
                .switchIfEmpty(
                        Mono.defer(
                                () -> Mono.error(new UsernameNotFoundException("User Not Found"))))
                .map(this::matToUserDetails);
    }

    private UserDetails matToUserDetails(ApplicationUser applicationUser) {
        return new AuthenticatedUser(applicationUser.id(), applicationUser.password());
    }
}
