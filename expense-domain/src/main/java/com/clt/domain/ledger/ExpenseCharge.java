package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import org.immutables.value.Value;

@Value.Immutable
public interface ExpenseCharge {
  String id();

  String expense();

  String groupId();

  Money dueAmount();

  String debtor();

  String creditor();
}
