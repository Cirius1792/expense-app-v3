package com.clt.domain.expense;

import com.clt.domain.commons.IdFactory;

public class ExpenseFactory {
  private final IdFactory idFactory;

  public ExpenseFactory(IdFactory idFactory) {
    this.idFactory = idFactory;
  }

  public Expense create(String description, Money amount, String owner, String groupId) {
    return ImmutableExpense.builder()
        .id(idFactory.newId())
        .description(description)
        .owner(owner)
        .amount(amount)
        .groupId(groupId)
        .build();
  }
}
