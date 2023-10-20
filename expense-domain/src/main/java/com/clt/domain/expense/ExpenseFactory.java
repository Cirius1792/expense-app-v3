package com.clt.domain.expense;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.group.Person;
import com.clt.usecase.ExpenseAggregate;
import com.clt.usecase.ImmutableExpenseAggregate;

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

  public ExpenseAggregate create(Expense e, Person owner) {
    return ImmutableExpenseAggregate.builder()
        .id(e.id())
        .description(e.description())
        .owner(owner)
        .amount(e.amount())
        .groupId(e.groupId())
        .build();
  }
}
