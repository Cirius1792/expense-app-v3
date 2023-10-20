package com.clt.view;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;
import org.immutables.value.Value;

@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE)
@Value.Immutable
public interface ExpenseAggregate {
  String id();

  String description();

  Money amount();

  Person owner();

  String groupId();
}
