package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.User;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;
import reactor.core.publisher.Mono;

public class RegisterUserUseCase implements UseCase {

  private final UserFactory userFactory;
  private final UserStore store;

  public RegisterUserUseCase(UserFactory userFactory, UserStore store) {
    this.userFactory = userFactory;
    this.store = store;
  }

  public Mono<User> register(String username) {
    return this.store.store(userFactory.create(null, username));
  }

  public Mono<User> register(String userId, String username){
    return this.store.store(userFactory.create(userId, username));
  }
}
