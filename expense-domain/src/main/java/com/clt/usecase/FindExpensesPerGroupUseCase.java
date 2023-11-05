package com.clt.usecase;

import com.clt.domain.commons.Page;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.PersonStore;
import com.clt.view.ExpenseAggregate;
import com.clt.view.ExpenseAggregateFactory;
import reactor.core.publisher.Flux;

public class FindExpensesPerGroupUseCase {
    private final ExpenseStore expenseStore;
    private final PersonStore personStore;

    public FindExpensesPerGroupUseCase(ExpenseStore expenseStore, PersonStore personStore) {
        this.expenseStore = expenseStore;
        this.personStore = personStore;
    }

    public Flux<ExpenseAggregate> retrieve(String groupId, int pageNumber, int pageSize) {
        return expenseStore.retrieveByGroup(groupId, new Page(pageSize, pageNumber))
                .flatMap(e -> personStore.retrieve(e.owner())
                        .map(p -> ExpenseAggregateFactory.fromDomain(e, p)));
    }
}
