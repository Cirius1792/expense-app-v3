package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.*;
import com.clt.domain.view.GroupAggregate;
import com.clt.domain.view.GroupAggregateFactory;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public class FindGroupUseCase implements UseCase {

  private final GroupStore groupStore;
  private final UserStore personStore;

  public FindGroupUseCase(GroupStore groupStore, UserStore personStore) {
    this.groupStore = groupStore;
    this.personStore = personStore;
  }

  public Mono<GroupAggregate> retrieve(@NonNull String groupId) {
    return groupStore
        .retrieve(groupId)
        .switchIfEmpty(Mono.error(new GroupNotFound()))
        .flatMap(
            g ->
                personStore
                    .retrieve(allMembers(g))
                    .collectList()
                    .transform(membersProducer -> buildAggregate(g, membersProducer)));
  }

  private Publisher<GroupAggregate> buildAggregate(Group g, Mono<List<User>> membersProducer) {
    return membersProducer.map(
        members ->
            GroupAggregateFactory.fromDomain(
                g,
                members.stream().filter(m -> m.getId().equals(g.getOwner())).findFirst().orElseThrow(),
                members));
  }

  private List<String> allMembers(Group g) {
    List<String> membersAndOwner = new ArrayList<>(g.getMembers());
    membersAndOwner.add(g.getOwner());
    return membersAndOwner;
  }
}
