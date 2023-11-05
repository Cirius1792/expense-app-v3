package com.clt.domain.group;

public abstract class NotFroundError extends RuntimeException {
  public NotFroundError() {}

  public NotFroundError(String resourceId) {
    super("Resource [%s] not found".formatted(resourceId));
  }
}
