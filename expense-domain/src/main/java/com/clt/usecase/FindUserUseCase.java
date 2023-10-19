package com.clt.usecase;

import com.clt.domain.group.Person;
import com.clt.domain.group.PersonNotFound;
import com.clt.domain.group.PersonStore;
import reactor.core.publisher.Mono;

public class FindUserUseCase {
  private final PersonStore personStore;

  public FindUserUseCase(PersonStore personStore) {
    this.personStore = personStore;
  }

  public Mono<Person> retrieve(String userId) {
    return personStore.retrieve(userId).switchIfEmpty(Mono.error(new PersonNotFound()));
  }
}
