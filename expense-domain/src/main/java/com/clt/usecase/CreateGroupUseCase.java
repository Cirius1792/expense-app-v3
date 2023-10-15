package com.clt.usecase;

import com.clt.domain.group.*;
import java.util.List;
import reactor.core.publisher.Mono;

public class CreateGroupUseCase {
  private final GroupFactory groupFactory;
  private final PersonStore personStore;
  private final GroupStore groupStore;

  public CreateGroupUseCase(
      GroupFactory groupFactory, PersonStore personStore, GroupStore groupStore) {
    this.groupFactory = groupFactory;
    this.personStore = personStore;
    this.groupStore = groupStore;
  }

  public Mono<Group> create(String groupName, String ownerId, List<String> memberIds) {
    Mono<Person> ownerProducer =
        personStore.retrieve(ownerId).switchIfEmpty(Mono.error(new PersonNotFound()));
    Mono<List<Person>> membersProducer = personStore.retrieve(memberIds).collectList();
    return Mono.zip(
            ownerProducer, membersProducer, (o, ms) -> groupFactory.create(groupName, o, ms))
        .doOnNext(groupStore::store);
  }
}
