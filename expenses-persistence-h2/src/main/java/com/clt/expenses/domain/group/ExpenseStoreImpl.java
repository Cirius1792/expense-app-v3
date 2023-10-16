package com.clt.expenses.domain.group;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ExpenseStoreImpl implements ExpenseStore {
    @Override
    public Mono<Expense> store(Expense expense) {
        return null;
    }
}
