package com.clt.domain.expense;

import org.immutables.value.Value;

@Value.Immutable
public interface Expense {
  String id();

  String description();

  Money amount();

  String owner();

  String groupId();
}
