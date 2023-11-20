package com.clt.usecase;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.commons.UseCase;
import com.clt.domain.expense.Money;
import com.clt.domain.ledger.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class PayUseCase implements UseCase {

  private final IdFactory idFactory;
  private final ExpenseChargeStore expenseChargeStore;
  private final BalanceService balanceService;

  public PayUseCase(
      IdFactory idFactory, ExpenseChargeStore expenseCharge, BalanceService balanceService) {
    this.idFactory = idFactory;
    this.expenseChargeStore = expenseCharge;
    this.balanceService = balanceService;
  }

  public Mono<Charge> pay(String groupId, String debtorId, String creditorId, Money paidAmount) {
    if (BigDecimal.ZERO.compareTo(paidAmount.getAmount()) >= 0)
      return Mono.error(new InvalidAmountError());
    return balanceService
        .retrieveBalance(debtorId, groupId)
        .map(balance -> this.verifyAmountToPay(balance, creditorId, paidAmount))
        .map(
            amount ->
                ImmutableCharge.builder()
                    .id(idFactory.newId())
                    .groupId(groupId)
                    .creditor(debtorId)
                    .debtor(creditorId)
                    .amount(amount)
                    .build())
        .flatMap(pc -> expenseChargeStore.store(pc).thenReturn(pc));
  }

  private Money verifyAmountToPay(Balance balance, String creditorId, Money paidAmount) {
    if (paidAmount.getAmount().compareTo(balance.getDueTo(creditorId).getAmount()) > 0)
      throw  new InvalidAmountError();
    return paidAmount;
  }
}
