package com.clt.usecase;

import com.clt.domain.commons.Page;
import com.clt.domain.commons.UseCase;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.UserStore;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.ExpenseAggregateFactory;
import reactor.core.publisher.Flux;

public class FindExpensesPerGroupUseCase implements UseCase {
  private final ExpenseStore expenseStore;
  private final UserStore personStore;

  public FindExpensesPerGroupUseCase(ExpenseStore expenseStore, UserStore personStore) {
    this.expenseStore = expenseStore;
    this.personStore = personStore;
  }

  public Flux<ExpenseAggregate> retrieve(String groupId, int pageNumber, int pageSize) {
    return expenseStore.retrieveByGroup(groupId, new Page(pageSize, pageNumber))
        .flatMap(e
            -> personStore.retrieve(e.getOwner())
                   .map(p -> ExpenseAggregateFactory.fromDomain(e, p)));
  }
}
