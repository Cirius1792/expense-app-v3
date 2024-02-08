package com.clt.domain.expense;

import com.clt.event.GenericEvent;
import com.clt.event.Observer;
import reactor.core.publisher.Mono;

public class NewExpenseNotifierNop implements Observer<ExpenseRecord> {
  @Override
  public Mono<GenericEvent<ExpenseRecord>> notify(ExpenseRecord event) {
    return Mono.empty();
  }
}
