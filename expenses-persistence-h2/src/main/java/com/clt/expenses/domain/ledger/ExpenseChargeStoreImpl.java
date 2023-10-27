package com.clt.expenses.domain.ledger;

import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ExpenseChargeStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ExpenseChargeStoreImpl implements ExpenseChargeStore {
    private final ExpenseChargeRepository expenseChargeRepository;
    private final ExpenseChargePersistenceMapper mapper;

    public ExpenseChargeStoreImpl(ExpenseChargeRepository expenseChargeRepository, ExpenseChargePersistenceMapper mapper) {
        this.expenseChargeRepository = expenseChargeRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ExpenseCharge> store(ExpenseCharge domain) {
        return Mono.just(mapper.toEntity(domain))
                .flatMap(expenseChargeRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ExpenseCharge> retrieve(String id) {
        return expenseChargeRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ExpenseCharge> retrieveBy(String debtor, String groupId) {
        return expenseChargeRepository.findByDebtorAndGroupId(debtor, groupId)
                .map(mapper::toDomain);
    }
}
