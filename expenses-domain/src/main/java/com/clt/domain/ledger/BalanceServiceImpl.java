package com.clt.domain.ledger;

import reactor.core.publisher.Mono;

public class BalanceServiceImpl implements BalanceService{
    private final ExpenseChargeStore expenseChargeStore;

    public BalanceServiceImpl(ExpenseChargeStore expenseChargeStore) {
        this.expenseChargeStore = expenseChargeStore;
    }

    @Override
    public Mono<Balance> retrieveBalance(String userId, String groupId) {
        return expenseChargeStore
                .retrieveBy(userId, groupId)
                .collectList()
                .map(charges -> new Balance(userId, charges));
    }

}
