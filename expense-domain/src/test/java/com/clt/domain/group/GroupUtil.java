package com.clt.domain.group;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GroupUtil {
    public static Group newGroup() {
        return ImmutableGroup.builder()
                .id(UUID.randomUUID().toString())
                .name("Test Group " + new Random().nextInt())
                .owner(PersonUtil.newPerson())
                .addMembers(PersonUtil.newPerson())
                .build();
    }

    public static Group newGroup(String name, List<Person> members) {
        assert members != null && !members.isEmpty();
        return ImmutableGroup.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .owner(members.get(0))
                .members(members)
                .build();
    }
}
