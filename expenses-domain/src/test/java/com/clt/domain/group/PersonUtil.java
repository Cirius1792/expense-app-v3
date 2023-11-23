package com.clt.domain.group;

import java.util.Random;
import java.util.UUID;

public class PersonUtil {
  public static User newPerson() {
    return ImmutableUser.builder()
        .id(UUID.randomUUID().toString())
        .username("User_" + new Random().nextInt())
        .build();
  }
}
