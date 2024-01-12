package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.User;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;

import com.clt.domain.registry.InvalidUsernameError;
import com.clt.event.Notifier;
import com.clt.usecase.pojo.NewUser;
import reactor.core.publisher.Mono;

public class RegisterUserUseCase implements UseCase {
    private final UserFactory userFactory;
    private final UserStore store;
    private final Notifier<NewUser> newUserNotifier;

    public RegisterUserUseCase(
            UserFactory userFactory,
            UserStore store,
            Notifier<NewUser> newUserNotifier) {
        this.userFactory = userFactory;
        this.store = store;
        this.newUserNotifier = newUserNotifier;
    }

    public RegisterUserUseCase(
            UserFactory userFactory,
            UserStore store
            ) {
        this.userFactory = userFactory;
        this.store = store;
        this.newUserNotifier = u -> Mono.empty();
    }

    public Mono<User> register(NewUser newUser) {
        return this.store
                .retrieve(newUser.getId())
                .flatMap(u -> Mono.<User>error(new InvalidUsernameError()))
                .switchIfEmpty(this.store.store(userFactory.create(newUser.getId())))
                .doOnSuccess(u -> newUserNotifier.notify(newUser).thenReturn(u));
    }


}
