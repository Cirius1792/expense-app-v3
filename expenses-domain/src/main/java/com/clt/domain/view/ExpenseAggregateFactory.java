package com.clt.domain.view;

import com.clt.domain.expense.Expense;
import com.clt.domain.group.User;

public class ExpenseAggregateFactory {
  public static ExpenseAggregate fromDomain(Expense e, User owner) {
    return ImmutableExpenseAggregate.builder()
        .id(e.getId())
        .description(e.getDescription())
        .owner(owner)
        .amount(e.getAmount())
        .groupId(e.getGroupId())
        .build();
  }
}
