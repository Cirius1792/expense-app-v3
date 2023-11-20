package com.clt.usecase;

import com.clt.domain.expense.Money;
import com.clt.domain.ledger.BalanceService;
import reactor.core.publisher.Mono;

public class RetrieveUserDebtUseCase {
    private final BalanceService balanceService;

    public RetrieveUserDebtUseCase(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public Mono<Money> retrieveFor(String groupId, String debtor, String creditor){
        return balanceService.retrieveBalance(debtor, groupId)
                .map(balance -> balance.getDueTo(creditor));
    }
}
