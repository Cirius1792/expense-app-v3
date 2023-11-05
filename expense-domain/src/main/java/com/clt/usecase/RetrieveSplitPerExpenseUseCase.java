package com.clt.usecase;

import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ExpenseChargeStore;
import reactor.core.publisher.Flux;

public class RetrieveSplitPerExpenseUseCase {
  private final ExpenseChargeStore expenseChargeStore;

  public RetrieveSplitPerExpenseUseCase(ExpenseChargeStore expenseChargeStore) {
    this.expenseChargeStore = expenseChargeStore;
  }

  public Flux<ExpenseCharge> retrieve(String expenseId) {
    return expenseChargeStore.retrieveBy(expenseId);
  }
}
