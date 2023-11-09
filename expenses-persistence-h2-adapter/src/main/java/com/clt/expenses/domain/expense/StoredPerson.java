package com.clt.expenses.domain.expense;

import com.clt.domain.group.Person;

public class StoredPerson implements Person {
  private String id;

  public StoredPerson(String id) {
    this.id = id;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public String username() {
    throw new RuntimeException("Not Implemented");
  }
}
