package com.clt.expenses.security;

import reactor.core.publisher.Mono;

public interface UserDetailsStore {
    Mono<ApplicationUser> findByUsername(String username);
    Mono<Void> store(ApplicationUser user);
}
