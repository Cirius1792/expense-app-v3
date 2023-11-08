package com.clt.usecase;

import com.clt.domain.expense.ExpenseNotFound;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.PersonStore;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.ExpenseAggregateFactory;
import reactor.core.publisher.Mono;

public class FindExpenseUseCase {
  private final ExpenseStore expenseStore;
  private final PersonStore personStore;

  public FindExpenseUseCase(ExpenseStore expenseStore, PersonStore personStore) {
    this.expenseStore = expenseStore;
    this.personStore = personStore;
  }

  public Mono<ExpenseAggregate> retrieveExpense(String expenseId) {
    return expenseStore
        .retrieve(expenseId)
        .switchIfEmpty(Mono.error(new ExpenseNotFound()))
        .flatMap(
            e ->
                personStore.retrieve(e.owner()).map(o -> ExpenseAggregateFactory.fromDomain(e, o)));
  }
}
