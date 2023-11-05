package com.clt.domain.expense;

import com.clt.event.GenericEvent;
import com.clt.event.Notifier;
import reactor.core.publisher.Mono;

public class NewExpenseNotifierNop implements Notifier<ExpenseRecord> {
  @Override
  public Mono<GenericEvent<ExpenseRecord>> notify(ExpenseRecord event) {
    return Mono.empty();
  }
}
