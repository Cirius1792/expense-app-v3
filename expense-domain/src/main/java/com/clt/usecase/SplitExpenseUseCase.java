package com.clt.usecase;

import com.clt.domain.expense.ExpenseRecord;
import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ExpenseSplitter;
import reactor.core.publisher.Flux;

public class SplitExpenseUseCase {
  private final ExpenseSplitter splitter;
  private final ExpenseChargeStore expenseChargeStore;

  public SplitExpenseUseCase(ExpenseSplitter splitter, ExpenseChargeStore expenseChargeStore) {
    this.splitter = splitter;
    this.expenseChargeStore = expenseChargeStore;
  }

  public Flux<ExpenseCharge> split(ExpenseRecord expense) {
    return Flux.fromIterable(splitter.split(expense.expense(), expense.group()))
        .flatMap(expenseChargeStore::store);
  }
}
