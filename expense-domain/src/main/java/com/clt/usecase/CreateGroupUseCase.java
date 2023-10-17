package com.clt.usecase;

import com.clt.domain.group.*;
import java.util.List;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

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

  public Mono<GroupAggregate> create(String groupName, String ownerId, List<String> memberIds) {
    Mono<Person> ownerProducer =
        personStore.retrieve(ownerId).switchIfEmpty(Mono.error(new PersonNotFound()));
    Mono<List<Person>> membersProducer = personStore.retrieve(memberIds).collectList();
    Mono<Group> g =
        Mono.just(groupFactory.create(groupName, ownerId, memberIds)).doOnNext(groupStore::store);
    return Mono.zip(ownerProducer, membersProducer, g).map(this::buildAggregate);
  }

  private GroupAggregate buildAggregate(Tuple3<Person, List<Person>, Group> objects) {
    Person owner = objects.getT1();
    List<Person> memebers = objects.getT2();
    Group group = objects.getT3();
    return ImmutableGroupAggregate.builder()
        .id(group.id())
        .name(group.name())
        .owner(owner)
        .addAllMembers(memebers)
        .addMembers(owner)
        .build();
  }
}
