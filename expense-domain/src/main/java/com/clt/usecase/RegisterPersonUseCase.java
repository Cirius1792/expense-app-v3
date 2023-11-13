package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.User;
import com.clt.domain.group.PersonFactory;
import com.clt.domain.group.UserStore;
import reactor.core.publisher.Mono;

public class RegisterPersonUseCase implements UseCase {

  private final PersonFactory personFactory;
  private final UserStore store;

  public RegisterPersonUseCase(PersonFactory personFactory, UserStore store) {
    this.personFactory = personFactory;
    this.store = store;
  }

  public Mono<User> register(String username) {
    return this.store.store(personFactory.create(username));
  }
}
