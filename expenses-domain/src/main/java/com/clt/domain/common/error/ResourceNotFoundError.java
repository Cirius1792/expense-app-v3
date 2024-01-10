
package com.clt.domain.common.error;

public class ResourceNotFoundError extends RuntimeException {
  public ResourceNotFoundError() {
    super("Resouce not found with the given id");
  }
}
