package com.clt.usecase;

import com.clt.domain.expense.ExpenseNotFound;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.PersonStore;
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
                personStore
                    .retrieve(e.owner())
                    .map(
                        o ->
                            ImmutableExpenseAggregate.builder()
                                .id(e.id())
                                .owner(o)
                                .description(e.description())
                                .amount(e.amount())
                                .groupId(e.groupId())
                                .build()));
  }
}
