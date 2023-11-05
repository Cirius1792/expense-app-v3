package com.clt.expenses.domain.expense;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ExpenseRepository extends ReactiveCrudRepository<ExpenseEntity, String> {
    Flux<ExpenseEntity> findByGroupId(String groupId, Pageable page);
}
