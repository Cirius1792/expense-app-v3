package com.clt.expenses.domain.ledger;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ExpenseChargeRepository
    extends ReactiveCrudRepository<ExpenseChargeEntity, String> {
  Flux<ExpenseChargeEntity> findByDebtorAndGroupId(String debtor, String groupId);

  Flux<ExpenseChargeEntity> findByCreditorAndGroupId(String creditor, String groupId);

  Flux<ExpenseChargeEntity> findByExpense(String expense);
}
