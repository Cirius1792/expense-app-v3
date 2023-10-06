package com.clt.domain.group;

import java.util.Random;
import java.util.UUID;

public class GroupUtil {
    public static Group newGroup(){
        return ImmutableGroup.builder()
                .id(UUID.randomUUID().toString())
                .name("Test Group "+new Random().nextInt())
                .owner(PersonUtil.newPerson())
                .addMembers(PersonUtil.newPerson())
                .build();
    }
}
