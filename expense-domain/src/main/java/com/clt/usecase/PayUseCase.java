package com.clt.usecase;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.expense.Money;
import com.clt.domain.ledger.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class PayUseCase {

  private final IdFactory idFactory;
  private final ExpenseChargeStore expenseChargeStore;

  public PayUseCase(IdFactory idFactory, ExpenseChargeStore expenseCharge) {
    this.idFactory = idFactory;
    this.expenseChargeStore = expenseCharge;
  }

  public Mono<Charge> pay(String groupId, String debtorId, String creditorId, Money paidAmount) {
    if (BigDecimal.ZERO.compareTo(paidAmount.getAmount()) >= 0)
      return Mono.error(new InvalidAmountError());
    return Mono.just(
            ImmutableCharge.builder()
                .id(idFactory.newId())
                .groupId(groupId)
                .creditor(debtorId)
                .debtor(creditorId)
                .amount(paidAmount)
                .build())
        .flatMap(pc -> expenseChargeStore.store(pc).thenReturn(pc));
  }
}
