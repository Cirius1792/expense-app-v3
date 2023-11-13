package com.clt.expenses.domain.expense;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ImmutableExpense;
import com.clt.domain.expense.Money;
import com.clt.expenses.domain.common.PersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpenseEntityMapper implements PersistenceMapper<ExpenseEntity, Expense> {

  @Override
  public ExpenseEntity toEntity(Expense expense) {
    ExpenseEntity entity = new ExpenseEntity();
    entity.setId(expense.getId());
    entity.setAmount(expense.getAmount().getAmount().toString());
    entity.setDescription(expense.getDescription());
    entity.setGroupId(expense.getGroupId());
    entity.setOwnerId(expense.getOwner());
    return entity;
  }

  @Override
  public Expense toDomain(ExpenseEntity expenseEntity) {
    return ImmutableExpense.builder()
        .id(expenseEntity.getId())
        .amount(Money.euros(expenseEntity.getAmount()))
        .description(expenseEntity.getDescription())
        .groupId(expenseEntity.getGroupId())
        .owner(expenseEntity.getOwnerId())
        .build();
  }
}
