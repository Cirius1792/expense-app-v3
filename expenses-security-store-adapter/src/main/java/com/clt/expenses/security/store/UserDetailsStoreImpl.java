package com.clt.expenses.security.store;

import com.clt.expenses.security.ApplicationUser;
import com.clt.expenses.security.UserDetailsStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserDetailsStoreImpl implements UserDetailsStore {
    private final ApplicationUserRepository applicationUserRepository;

    public UserDetailsStoreImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Mono<ApplicationUser> findByUsername(String username) {
        return applicationUserRepository
                .findById(username)
                .map(user -> new ApplicationUser(user.getId(), user.getPassword()));
    }

    @Override
    public Mono<Void> store(ApplicationUser user) {
        return Mono.just(new ApplicationUserEntity(user.id(), user.password()))
                .flatMap(applicationUserRepository::save)
                .then();
    }
}
