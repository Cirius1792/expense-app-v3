package com.clt.domain.ledger;

import reactor.core.publisher.Mono;

public interface ExpenseChargeStore {

    Mono<ExpenseCharge> store(ExpenseCharge expenseCharge);
}
