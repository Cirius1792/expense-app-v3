package com.clt.domain.view;

import com.clt.domain.group.Group;
import com.clt.domain.group.User;
import reactor.util.annotation.NonNull;

public class GroupAggregateFactory {
  private GroupAggregateFactory() {}

  public static GroupAggregate fromDomain(
          @NonNull Group group, @NonNull User owner, @NonNull Iterable<? extends User> members) {
    return ImmutableGroupAggregate.builder()
        .id(group.getId())
        .name(group.getName())
        .owner(owner)
        .addAllMembers(members)
        .addMembers(owner)
        .build();
  }
}
