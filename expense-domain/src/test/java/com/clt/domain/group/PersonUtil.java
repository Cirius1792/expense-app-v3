package com.clt.domain.group;

import java.util.Random;
import java.util.UUID;

public class PersonUtil {
  public static Person newPerson() {
    return ImmutablePerson.builder().id(UUID.randomUUID().toString()).name("User_"+new Random().nextInt()).build();
  }
}
