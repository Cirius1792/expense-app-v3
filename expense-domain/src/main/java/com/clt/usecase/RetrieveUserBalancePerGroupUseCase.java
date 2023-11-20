package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.expense.Money;
import com.clt.domain.group.PersonNotFound;
import com.clt.domain.group.UserStore;
import com.clt.domain.ledger.Balance;
import com.clt.domain.ledger.ExpenseChargeStore;
import reactor.core.publisher.Mono;

public class RetrieveUserBalancePerGroupUseCase implements UseCase {
  private final UserStore personStore;
  private final ExpenseChargeStore expenseChargeStore;

  public RetrieveUserBalancePerGroupUseCase(
          UserStore personStore, ExpenseChargeStore expenseChargeStore) {
    this.personStore = personStore;
    this.expenseChargeStore = expenseChargeStore;
  }

  public Mono<Money> retrieve(String user, String groupId) {
    return personStore
        .retrieve(user)
        .switchIfEmpty(Mono.error(PersonNotFound::new))
        .zipWith(expenseChargeStore.retrieveBy(user, groupId).collectList())
        .map(p -> new Balance(p.getT1().getId(), p.getT2()))
        .map(Balance::balance);
  }
}
