package com.clt.expenses.domain.expense;

import com.clt.domain.group.User;

public class StoredUser implements User {
  private String id;

  public StoredUser(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getUsername() {
    throw new RuntimeException("Not Implemented");
  }
}
