package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.User;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;

import com.clt.domain.registry.InvalidUsernameError;
import com.clt.event.Observer;
import com.clt.usecase.pojo.NewUser;
import reactor.core.publisher.Mono;

public class CreateUserUseCase implements UseCase {
    private final UserFactory userFactory;
    private final UserStore store;
    private final Observer<NewUser> newUserNotifier;

    public CreateUserUseCase(
            UserFactory userFactory,
            UserStore store,
            Observer<NewUser> newUserNotifier) {
        this.userFactory = userFactory;
        this.store = store;
        this.newUserNotifier = newUserNotifier;
    }

    public CreateUserUseCase(
            UserFactory userFactory,
            UserStore store
            ) {
        this.userFactory = userFactory;
        this.store = store;
        this.newUserNotifier = u -> Mono.empty();
    }
    /**
     * Registers a new user.
     *
     * @param  newUser the new user to be registered
     * @return         the registered user
     */
    public Mono<User> register(NewUser newUser) {
        return this.store
                .retrieve(newUser.getId())
                .flatMap(u -> Mono.<User>error(new InvalidUsernameError()))
                .switchIfEmpty(this.store.store(userFactory.create(newUser.getId())))
                .doOnSuccess(u -> newUserNotifier.notify(newUser).thenReturn(u));
    }


}
