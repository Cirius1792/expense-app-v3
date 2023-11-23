package com.clt.domain.expense;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableExpense.class)
@JsonDeserialize(as = ImmutableExpense.class)
public interface Expense {
  String getId();

  String getDescription();

  Money getAmount();

  String getOwner();

  String getGroupId();
}
