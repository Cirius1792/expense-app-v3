package com.clt.expenses.security;

import com.clt.domain.group.UserStore;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

//@Service
public class ExpensesUserDetailService implements ReactiveUserDetailsService {

    private final UserStore userStore;

    public ExpensesUserDetailService(UserStore userStore) {
        this.userStore = userStore;
    }

    public Mono<UserDetails> findByUsername(String username) {
        return Mono.empty();
    }
}
