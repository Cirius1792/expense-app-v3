package com.clt.usecase;

import com.clt.domain.expense.Money;
import com.clt.domain.group.PersonStore;
import com.clt.domain.ledger.Balance;
import com.clt.domain.ledger.ExpenseChargeStore;
import reactor.core.publisher.Mono;

public class RetrieveUserBalancePerGroupUseCase {
  private final PersonStore personStore;
  private final ExpenseChargeStore expenseChargeStore;

  public RetrieveUserBalancePerGroupUseCase(
      PersonStore personStore, ExpenseChargeStore expenseChargeStore) {
    this.personStore = personStore;
    this.expenseChargeStore = expenseChargeStore;
  }

  public Mono<Money> retrieve(String personId, String groupId) {
    return personStore
        .retrieve(personId)
        .map(p -> new Balance(p, expenseChargeStore.retrieveBy(p.id(), groupId).toIterable()))
        .map(Balance::balance);
  }
}
