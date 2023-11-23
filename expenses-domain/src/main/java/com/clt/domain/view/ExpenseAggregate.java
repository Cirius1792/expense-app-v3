package com.clt.domain.view;

import com.clt.domain.expense.Money;
import com.clt.domain.group.User;
import org.immutables.value.Value;

@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE)
@Value.Immutable
public interface ExpenseAggregate {
  String getId();

  String getDescription();

  Money getAmount();

  User getOwner();

  String getGroupId();
}
