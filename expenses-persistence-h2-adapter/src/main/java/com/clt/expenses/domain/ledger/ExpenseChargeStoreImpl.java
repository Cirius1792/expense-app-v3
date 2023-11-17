package com.clt.expenses.domain.ledger;

import com.clt.domain.ledger.Charge;
import com.clt.domain.ledger.ExpenseChargeStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ExpenseChargeStoreImpl implements ExpenseChargeStore {
  private final ExpenseChargeRepository expenseChargeRepository;
  private final ExpenseChargePersistenceMapper mapper;

  public ExpenseChargeStoreImpl(
      ExpenseChargeRepository expenseChargeRepository, ExpenseChargePersistenceMapper mapper) {
    this.expenseChargeRepository = expenseChargeRepository;
    this.mapper = mapper;
  }

  @Override
  public Mono<Charge> store(Charge domain) {
    return Mono.just(mapper.toEntity(domain))
        .flatMap(expenseChargeRepository::save)
        .map(mapper::toDomain);
  }

  @Override
  public Mono<Charge> retrieve(String id) {
    return expenseChargeRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Flux<Charge> retrieveBy(String subject, String groupId) {
    return expenseChargeRepository
        .findByDebtorAndGroupId(subject, groupId)
        .concatWith(expenseChargeRepository.findByCreditorAndGroupId(subject, groupId))
        .map(mapper::toDomain);
  }

  @Override
  public Flux<Charge> retrieveBy(String expenseId) {
    return expenseChargeRepository.findByExpense(expenseId).map(mapper::toDomain);
  }
}
