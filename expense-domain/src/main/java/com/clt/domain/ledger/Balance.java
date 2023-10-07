package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface Balance {
  Map<Person, Money> owned(Person debtor);

  default Money totalOwned(Person debtor) {
    return Optional.ofNullable(owned(debtor))
            .orElse(new HashMap<>())
            .values().stream()
            .reduce(Money.euros(0), Money::plus);
  }
}
