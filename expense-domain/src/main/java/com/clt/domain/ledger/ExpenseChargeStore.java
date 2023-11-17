package com.clt.domain.ledger;

import com.clt.domain.commons.Store;
import reactor.core.publisher.Flux;

public interface ExpenseChargeStore extends Store<Charge, String> {
  Flux<Charge> retrieveBy(String debtor, String groupId);

  Flux<Charge> retrieveBy(String expenseId);
}
