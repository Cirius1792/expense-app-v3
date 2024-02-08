package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.*;
import com.clt.domain.view.GroupAggregate;
import com.clt.domain.view.GroupAggregateFactory;
import java.util.List;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

public class CreateGroupWithMembersUseCase implements UseCase {
  private final GroupFactory groupFactory;
  private final UserStore personStore;
  private final GroupStore groupStore;

  public CreateGroupWithMembersUseCase(
          GroupFactory groupFactory, UserStore personStore, GroupStore groupStore) {
    this.groupFactory = groupFactory;
    this.personStore = personStore;
    this.groupStore = groupStore;
  }

  public Mono<GroupAggregate> create(String groupName, String ownerId, List<String> memberIds) {
    Mono<User> ownerProducer =
        personStore.retrieve(ownerId).switchIfEmpty(Mono.error(new PersonNotFound()));
    Mono<List<User>> membersProducer = personStore.retrieve(memberIds).collectList();
    Mono<Group> g =
        Mono.just(groupFactory.create(groupName, ownerId, memberIds)).flatMap(groupStore::store);
    return Mono.zip(ownerProducer, membersProducer, g).map(this::buildAggregate);
  }

  private GroupAggregate buildAggregate(Tuple3<User, List<User>, Group> objects) {
    User owner = objects.getT1();
    List<User> memebers = objects.getT2();
    Group group = objects.getT3();
    return GroupAggregateFactory.fromDomain(group, owner, memebers);
  }
}
