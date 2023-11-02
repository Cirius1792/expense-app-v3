package com.clt.usecase;

import com.clt.domain.expense.Money;
import com.clt.domain.group.PersonNotFound;
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

  public Mono<Money> retrieve(String user, String groupId) {
    return personStore
        .retrieve(user)
        .switchIfEmpty(Mono.error(PersonNotFound::new))
        .zipWith(expenseChargeStore.retrieveBy(user, groupId).collectList())
        .map(p -> new Balance(p.getT1(), p.getT2()))
        .map(Balance::balance);
  }
}
