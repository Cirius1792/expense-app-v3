package com.clt.domain.expense;

import com.clt.domain.group.Person;
import org.immutables.value.Value;

@Value.Immutable
public interface Expense {
  String id();

  String description();

  Money amount();

  Person owner();

  String groupId();
}
