package com.clt.expenses.domain.expense;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ReactiveCrudRepository<ExpenseEntity, String> {}
