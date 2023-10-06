package com.clt.domain.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupManagerServiceTest {

    private static final String GROUP_NAME = "group-x";
    private static final Person GROUP_OWNER = ImmutablePerson.builder()
            .id("xxx")
            .name("Mario Rossi")
            .build();

    GroupManagerService service;

    @DisplayName("Given a name and an owner " +
            "When creating the group " +
            "Then A group with the given name and owner is created " +
            "And the group id is not null")
    @Test
    void create_new_group_test(){
        Group actual = service.create(GROUP_NAME, GROUP_OWNER);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.id(), "Missing id");
        Assertions.assertEquals(GROUP_NAME, actual.name(), "Wrong group name");
        Assertions.assertEquals(GROUP_OWNER, actual.owner(), "Wrong owner");
    }

}