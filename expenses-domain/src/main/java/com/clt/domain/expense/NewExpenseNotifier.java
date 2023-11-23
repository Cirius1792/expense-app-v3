package com.clt.domain.expense;

import reactor.core.publisher.Mono;

public interface NewExpenseNotifier {
  default Mono<Void> notify(ExpenseRecord expenseRecord) {
    return Mono.empty();
  }
}
