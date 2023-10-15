package com.clt.domain.expense;

import reactor.core.publisher.Mono;

public interface ExpenseStore {
    Mono<Expense> store(Expense expense);
}
