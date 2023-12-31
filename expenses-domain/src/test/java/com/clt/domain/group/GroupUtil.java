package com.clt.domain.group;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupUtil {
  public static Group newGroup() {
    return ImmutableGroup.builder()
        .id(UUID.randomUUID().toString())
        .name("Test Group " + new Random().nextInt())
        .owner(UUID.randomUUID().toString())
        .addMembers(UUID.randomUUID().toString())
        .build();
  }

  public static Group newGroup(String name, List<User> members) {
    assert members != null && !members.isEmpty();
    return ImmutableGroup.builder()
        .id(UUID.randomUUID().toString())
        .name(name)
        .owner(members.get(0).getId())
        .members(members.stream().map(User::getId).toList())
        .build();
  }

  public static Group newGroup(int nMembers) {
    assert nMembers > 1;
    User owner = PersonUtil.newPerson();
    return ImmutableGroup.builder()
        .id(UUID.randomUUID().toString())
        .name("Test Group " + new Random().nextInt())
        .owner(owner.getId())
        .members(
            IntStream.range(0, nMembers)
                .mapToObj($ -> UUID.randomUUID().toString())
                .collect(Collectors.toList()))
        .build();
  }
}
