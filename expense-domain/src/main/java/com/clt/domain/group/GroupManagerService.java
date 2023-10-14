package com.clt.domain.group;

import com.clt.domain.commons.IdFactory;

import java.util.List;

public class GroupManagerService {
  private final IdFactory groupIdFactory;

  public GroupManagerService(IdFactory groupIdFactory) {
    this.groupIdFactory = groupIdFactory;
  }

  public Group create(String name, Person owner) {
    return ImmutableGroup.builder()
        .id(groupIdFactory.newId())
        .name(name)
        .owner(owner)
        .addMembers(owner)
        .build();
  }
  public Group create(String name, Person owner, List<Person> members) {
    return ImmutableGroup.builder()
            .id(groupIdFactory.newId())
            .name(name)
            .owner(owner)
            .addMembers(owner)
            .members(members)
            .build();
  }

  public Group add(Group group, Person member) {
    return ImmutableGroup.builder().from(group).addMembers(member).build();
  }
}
