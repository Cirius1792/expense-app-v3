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
    entity.setId(expense.id());
    entity.setAmount(expense.amount().getAmount().toString());
    entity.setDescription(expense.description());
    entity.setGroupId(expense.groupId());
    entity.setOwnerId(expense.owner());
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
