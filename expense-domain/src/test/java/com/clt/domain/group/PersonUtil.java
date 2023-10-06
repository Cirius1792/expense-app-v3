package com.clt.domain.group;

import java.util.UUID;

public class PersonUtil {
    public static Person newPerson(){
        return ImmutablePerson.builder()
                .id(UUID.randomUUID().toString())
                .name("Mario Rossi")
                .build();
    }
}
