package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import org.immutables.value.Value;
import reactor.util.annotation.Nullable;

@Value.Immutable
public interface Charge {
  String getId();

  String getGroupId();

  Money getAmount();

  String getDebtor();

  @Nullable
  String getExpense();

  String getCreditor();
}
