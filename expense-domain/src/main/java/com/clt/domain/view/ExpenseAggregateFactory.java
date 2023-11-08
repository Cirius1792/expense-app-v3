package com.clt.domain.view;

import com.clt.domain.expense.Expense;
import com.clt.domain.group.Person;

public class ExpenseAggregateFactory {
  public static ExpenseAggregate fromDomain(Expense e, Person owner) {
    return ImmutableExpenseAggregate.builder()
        .id(e.id())
        .description(e.description())
        .owner(owner)
        .amount(e.amount())
        .groupId(e.groupId())
        .build();
  }
}
