package com.clt.domain.group;

import com.clt.domain.commons.IdFactory;

import java.util.Optional;

public class UserFactory {
  private final IdFactory idFactory;

  public UserFactory(IdFactory idFactory) {
    this.idFactory = idFactory;
  }


  public User create(String username) {
    return this.create(null, username);
  }
  public User create(String userId, String username) {
    String id = Optional.ofNullable(userId).orElse(idFactory.newId());
    return ImmutableUser.builder().id(id).username(username).build();
  }
}
