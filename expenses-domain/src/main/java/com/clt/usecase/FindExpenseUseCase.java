package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.expense.ExpenseNotFound;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.UserStore;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.ExpenseAggregateFactory;
import reactor.core.publisher.Mono;

public class FindExpenseUseCase implements UseCase {
  private final ExpenseStore expenseStore;
  private final UserStore personStore;

  public FindExpenseUseCase(ExpenseStore expenseStore, UserStore personStore) {
    this.expenseStore = expenseStore;
    this.personStore = personStore;
  }

  public Mono<ExpenseAggregate> retrieveExpense(String expenseId) {
    return expenseStore
        .retrieve(expenseId)
        .switchIfEmpty(Mono.error(new ExpenseNotFound()))
        .flatMap(
            e ->
                personStore.retrieve(e.getOwner()).map(o -> ExpenseAggregateFactory.fromDomain(e, o)));
  }
}
