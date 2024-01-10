package com.clt.expenses.expense.response;

public class ResourceNotFoundResponse {
  private String resourceId;
  private String message;
  public String getResourceId() {
    return resourceId;
  }
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  public static ResourceNotFoundResponse create(String resourceId, String message) {
    ResourceNotFoundResponse response = new ResourceNotFoundResponse();
    response.setResourceId(resourceId);
    response.setMessage(message);
    return response;
  }
}
