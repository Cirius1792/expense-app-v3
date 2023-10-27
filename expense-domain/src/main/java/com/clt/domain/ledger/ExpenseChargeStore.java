package com.clt.domain.ledger;

import com.clt.domain.commons.Store;
import reactor.core.publisher.Flux;

public interface ExpenseChargeStore extends Store<ExpenseCharge, String> {
  Flux<ExpenseCharge> retrieveBy(String debtor, String groupId);
}
