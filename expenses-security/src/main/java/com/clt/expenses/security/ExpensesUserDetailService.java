package com.clt.expenses.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExpensesUserDetailService implements ReactiveUserDetailsService {

    private final UserDetailsStore userDetailsStore;

    public ExpensesUserDetailService(UserDetailsStore userDetailsStore) {
        this.userDetailsStore = userDetailsStore;
    }

    public Mono<UserDetails> findByUsername(String username) {
        return userDetailsStore
                .findByUsername(username)
                .switchIfEmpty(
                        Mono.defer(
                                () -> Mono.error(new UsernameNotFoundException("User Not Found"))))
                .map(this::matToUserDetails);
    }

    private UserDetails matToUserDetails(ApplicationUser applicationUser) {
        return new AuthenticatedUser(applicationUser.id(), applicationUser.password());
    }
}
