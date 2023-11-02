package com.clt.view;

import com.clt.domain.group.Group;
import com.clt.domain.group.Person;
import reactor.util.annotation.NonNull;

public class GroupAggregateFactory {
  private GroupAggregateFactory() {}

  public static GroupAggregate fromDomain(
      @NonNull Group group, @NonNull Person owner, @NonNull Iterable<? extends Person> members) {
    return ImmutableGroupAggregate.builder()
        .id(group.id())
        .name(group.name())
        .owner(owner)
        .addAllMembers(members)
        .addMembers(owner)
        .build();
  }
}
