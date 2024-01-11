package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.User;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;

import com.clt.usecase.pojo.NewUser;
import reactor.core.publisher.Mono;

public class RegisterUserUseCase implements UseCase {

    private final UserFactory userFactory;
    private final UserStore store;

    public RegisterUserUseCase(UserFactory userFactory, UserStore store) {
        this.userFactory = userFactory;
        this.store = store;
    }

    public Mono<User> register(NewUser newUser) {
        return this.store.store(userFactory.create(newUser.getId()));
    }
}
