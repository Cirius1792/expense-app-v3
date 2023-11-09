package com.clt.expenses.ledger;

import com.clt.domain.ledger.ExpenseCharge;
import com.clt.expenses.ledger.response.ExpenseSplitDto;
import org.springframework.stereotype.Component;

@Component
public class ExpenseSplitMapper {
  public ExpenseSplitDto toDto(ExpenseCharge domain) {
    return new ExpenseSplitDto(
        domain.id(),
        domain.expense(),
        domain.groupId(),
        domain.dueAmount().getAmount(),
        "EUR",
        domain.debtor(),
        domain.creditor());
  }
}
