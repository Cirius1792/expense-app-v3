package com.clt.domain.ledger;

import reactor.core.publisher.Mono;

public interface BalanceService {
    Mono<Balance> retrieveBalance(String userId, String groupId);
}
