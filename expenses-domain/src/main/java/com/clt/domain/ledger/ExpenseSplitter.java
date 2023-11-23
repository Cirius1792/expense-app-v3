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

  public List<Charge> split(Expense expense, Group group) {
    long debtors = group.getMembers().size();
    Money dueProCapite = expense.getAmount().divide(BigDecimal.valueOf(debtors));
    return group.getMembers().stream()
        .filter(el -> !expense.getOwner().equals(el))
        .map(
            debtor ->
                ImmutableCharge.builder()
                    .id(idFactory.newId())
                    .debtor(debtor)
                    .creditor(expense.getOwner())
                    .amount(dueProCapite)
                    .expense(expense.getId())
                    .groupId(group.getId())
                    .build())
        .collect(Collectors.toList());
  }
}
