package com.clt.expenses.expense.response;

import com.clt.expenses.expense.request.MoneyDto;
import com.clt.expenses.user.response.UserResponseDto;

public class ExpenseResponse {
  private String id;
  private String groupId;
  private String description;
  private MoneyDto amount;
  private UserResponseDto owner;

  @Override
  public String toString() {
    return "ExpenseResponse{"
        + "id='"
        + id
        + '\''
        + ", groupId='"
        + groupId
        + '\''
        + ", description='"
        + description
        + '\''
        + ", amount="
        + amount
        + ", owner="
        + owner
        + '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MoneyDto getAmount() {
    return amount;
  }

  public void setAmount(MoneyDto amount) {
    this.amount = amount;
  }

  public UserResponseDto getOwner() {
    return owner;
  }

  public void setOwner(UserResponseDto owner) {
    this.owner = owner;
  }
}
