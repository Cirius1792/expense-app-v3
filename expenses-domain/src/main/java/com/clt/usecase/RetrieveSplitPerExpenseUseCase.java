package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.ledger.Charge;
import com.clt.domain.ledger.ExpenseChargeStore;
import reactor.core.publisher.Flux;

public class RetrieveSplitPerExpenseUseCase implements UseCase {
  private final ExpenseChargeStore expenseChargeStore;

  public RetrieveSplitPerExpenseUseCase(ExpenseChargeStore expenseChargeStore) {
    this.expenseChargeStore = expenseChargeStore;
  }

  public Flux<Charge> retrieve(String expenseId) {
    return expenseChargeStore.retrieveBy(expenseId);
  }
}
