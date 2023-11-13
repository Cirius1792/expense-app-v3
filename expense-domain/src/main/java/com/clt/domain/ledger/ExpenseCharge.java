package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import org.immutables.value.Value;

@Value.Immutable
public interface ExpenseCharge {
  String getId();

  String getExpense();

  String getGroupId();

  Money getAmount();

  String getDebtor();

  String getCreditor();
}
