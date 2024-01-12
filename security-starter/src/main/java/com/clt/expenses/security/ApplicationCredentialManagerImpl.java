package com.clt.expenses.security;

import com.clt.domain.registry.InvalidUsernameError;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ApplicationCredentialManagerImpl implements ApplicationCredentialManager {
    private final UserDetailsStore userDetailsStore;
    private final PasswordEncoder passwordEncoder;

    public ApplicationCredentialManagerImpl(
            UserDetailsStore userDetailsStore, PasswordEncoder passwordEncoder) {
        this.userDetailsStore = userDetailsStore;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<ApplicationUser> retrieve(String username) {
        return userDetailsStore.findByUsername(username);
    }

    @Override
    public Mono<ApplicationUser> register(String username, String password) {
        return this.userDetailsStore
                .findByUsername(username)
                .flatMap(u -> Mono.<ApplicationUser>error(new InvalidUsernameError()))
                .switchIfEmpty(
                        Mono.defer(() -> Mono.just(passwordEncoder.encode(password)))
                                .flatMap(
                                        encodedPassword -> {
                                            ApplicationUser user =
                                                    new ApplicationUser(username, encodedPassword);
                                            return userDetailsStore.store(user).thenReturn(user);
                                        }));
    }
}
