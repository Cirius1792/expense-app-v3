package com.clt.expenses.expense.request;

public class CreateExpenseRequestDto {
  private String description;
  private MoneyDto amount;
  private String ownerId;

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

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }
}
