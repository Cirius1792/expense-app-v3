package com.clt.domain.group;

import com.clt.domain.commons.IdFactory;
import java.util.List;

public class GroupFactory {
  private final IdFactory groupIdFactory;

  public GroupFactory(IdFactory groupIdFactory) {
    this.groupIdFactory = groupIdFactory;
  }

  public Group create(String name, String owner) {
    return ImmutableGroup.builder()
        .id(groupIdFactory.newId())
        .name(name)
        .owner(owner)
        .addMembers(owner)
        .build();
  }

  public Group create(String name, String owner, List<String> members) {
    return ImmutableGroup.builder()
        .id(groupIdFactory.newId())
        .name(name)
        .owner(owner)
        .addMembers(owner)
        .members(members)
        .build();
  }

  public Group add(Group group, String member) {
    return ImmutableGroup.builder().from(group).addMembers(member).build();
  }
}
