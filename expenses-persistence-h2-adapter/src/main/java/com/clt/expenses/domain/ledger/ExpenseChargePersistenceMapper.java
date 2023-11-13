package com.clt.expenses.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ImmutableExpenseCharge;
import com.clt.expenses.domain.common.PersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpenseChargePersistenceMapper
    implements PersistenceMapper<ExpenseChargeEntity, ExpenseCharge> {
  @Override
  public ExpenseChargeEntity toEntity(ExpenseCharge domain) {
    ExpenseChargeEntity entity = new ExpenseChargeEntity();
    entity.setId(domain.getId());
    entity.setExpense(domain.getExpense());
    entity.setCreditor(domain.getCreditor());
    entity.setDebtor(domain.getDebtor());
    entity.setDueAmount(domain.getAmount().getAmount().toString());
    entity.setGroupId(domain.getGroupId());
    return entity;
  }

  @Override
  public ExpenseCharge toDomain(ExpenseChargeEntity expenseChargeEntity) {
    return ImmutableExpenseCharge.builder()
        .id(expenseChargeEntity.getId())
        .expense(expenseChargeEntity.getExpense())
        .groupId(expenseChargeEntity.getGroupId())
        .debtor(expenseChargeEntity.getDebtor())
        .creditor(expenseChargeEntity.getCreditor())
        .amount(Money.euros(expenseChargeEntity.getDueAmount()))
        .build();
  }
}
