package com.clt.expenses.security;

import reactor.core.publisher.Mono;

public interface ApplicationCredentialManager {

    Mono<ApplicationUser> retrieve(String username);
    Mono<ApplicationUser> register(String username, String password);
}
