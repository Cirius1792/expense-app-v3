package com.clt.expenses.domain.expense;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ExpenseStoreImpl implements ExpenseStore {
  private final ExpenseRepository expenseRepository;
  private final ExpenseEntityMapper expenseMapper;

  public ExpenseStoreImpl(ExpenseRepository expenseRepository, ExpenseEntityMapper expenseMapper) {
    this.expenseRepository = expenseRepository;
    this.expenseMapper = expenseMapper;
  }

  @Override
  public Mono<Expense> store(Expense expense) {
    ExpenseEntity entity = expenseMapper.toEntity(expense);
    return this.expenseRepository
        .findById(expense.id())
        .map(this::setAsNew)
        .switchIfEmpty(Mono.just(entity))
        .flatMap(expenseRepository::save)
        .map(expenseMapper::toDomain);
  }

  private ExpenseEntity setAsNew(ExpenseEntity entity) {
    entity.setNew(false);
    return entity;
  }

  @Override
  public Mono<Expense> retrieve(String expenseId) {
    return this.expenseRepository.findById(expenseId).map(expenseMapper::toDomain);
  }
}
