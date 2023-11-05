package com.clt.domain.group;

import com.clt.domain.commons.IdFactory;

public class PersonFactory {
  private final IdFactory idFactory;

  public PersonFactory(IdFactory idFactory) {
    this.idFactory = idFactory;
  }

  public Person create(String username) {
    return ImmutablePerson.builder().id(idFactory.newId()).username(username).build();
  }
}
