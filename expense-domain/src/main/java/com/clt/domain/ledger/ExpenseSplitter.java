package com.clt.domain.ledger;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.expense.Expense;
import com.clt.domain.expense.Money;
import com.clt.domain.group.Group;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseSplitter {
  private final IdFactory idFactory;

  public ExpenseSplitter(IdFactory idFactory) {
    this.idFactory = idFactory;
  }

  public List<ExpenseCharge> split(Expense expense, Group group) {
    long debtors = group.members().size();
    Money dueProCapite = expense.amount().divide(BigDecimal.valueOf(debtors));
    return group.members().stream()
        .filter(el -> !expense.owner().equals(el))
        .map(
            debtor ->
                ImmutableExpenseCharge.builder()
                    .id(idFactory.newId())
                    .debtor(debtor)
                    .creditor(expense.owner())
                    .dueAmount(dueProCapite)
                    .expense(expense.id())
                    .groupId(group.id())
                    .build())
        .collect(Collectors.toList());
  }
}
