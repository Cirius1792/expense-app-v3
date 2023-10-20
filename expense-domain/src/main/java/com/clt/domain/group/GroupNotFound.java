package com.clt.domain.group;

public class GroupNotFound extends NotFroundError {
  public GroupNotFound() {
  }

  public GroupNotFound(String resourceId) {
    super(resourceId);
  }
}
