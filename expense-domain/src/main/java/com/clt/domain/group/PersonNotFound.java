package com.clt.domain.group;

public class PersonNotFound extends RuntimeException {
  public PersonNotFound() {}

  public PersonNotFound(String message) {
    super(message);
  }
}
