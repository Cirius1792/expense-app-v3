package com.clt.expenses.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.ledger.Charge;
import com.clt.domain.ledger.ImmutableCharge;
import com.clt.expenses.domain.common.PersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpenseChargePersistenceMapper
    implements PersistenceMapper<ExpenseChargeEntity, Charge> {
  @Override
  public ExpenseChargeEntity toEntity(Charge domain) {
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
  public Charge toDomain(ExpenseChargeEntity expenseChargeEntity) {
    return ImmutableCharge.builder()
        .id(expenseChargeEntity.getId())
        .expense(expenseChargeEntity.getExpense())
        .groupId(expenseChargeEntity.getGroupId())
        .debtor(expenseChargeEntity.getDebtor())
        .creditor(expenseChargeEntity.getCreditor())
        .amount(Money.euros(expenseChargeEntity.getDueAmount()))
        .build();
  }
}
