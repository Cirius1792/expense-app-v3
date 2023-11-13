package com.clt.expenses.ledger;

import com.clt.domain.ledger.ExpenseCharge;
import com.clt.expenses.ledger.response.ExpenseSplitDto;
import org.springframework.stereotype.Component;

@Component
public class ExpenseSplitMapper {
  public ExpenseSplitDto toDto(ExpenseCharge domain) {
    return new ExpenseSplitDto(
        domain.getId(),
        domain.getExpense(),
        domain.getGroupId(),
        domain.getAmount().getAmount(),
        "EUR",
        domain.getDebtor(),
        domain.getCreditor());
  }
}
