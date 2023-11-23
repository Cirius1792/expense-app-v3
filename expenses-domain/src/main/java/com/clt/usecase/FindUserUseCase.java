package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.User;
import com.clt.domain.group.PersonNotFound;
import com.clt.domain.group.UserStore;
import reactor.core.publisher.Mono;

public class FindUserUseCase implements UseCase {
  private final UserStore personStore;

  public FindUserUseCase(UserStore personStore) {
    this.personStore = personStore;
  }

  public Mono<User> retrieve(String userId) {
    return personStore.retrieve(userId).switchIfEmpty(Mono.error(new PersonNotFound()));
  }
}
