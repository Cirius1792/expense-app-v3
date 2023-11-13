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
    entity.setId(domain.id());
    entity.setExpense(domain.expense());
    entity.setCreditor(domain.creditor());
    entity.setDebtor(domain.debtor());
    entity.setDueAmount(domain.amount().getAmount().toString());
    entity.setGroupId(domain.groupId());
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
