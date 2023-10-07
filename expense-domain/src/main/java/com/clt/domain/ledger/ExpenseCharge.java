package com.clt.domain.ledger;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;
import org.immutables.value.Value;

@Value.Immutable
public interface ExpenseCharge {
  String id();

  Expense expense();

  String groupId();

  Money dueAmount();

  Person debtor();

  default Person creditor() {
    return expense().owner();
  }
}
