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

  public Mono<Charge> pay(String groupId, String payer, String payed, Money paidAmount) {
    if (BigDecimal.ZERO.compareTo(paidAmount.getAmount()) >= 0)
      return Mono.error(new InvalidAmountError(paidAmount));
    return balanceService
        .retrieveBalance(payer, groupId)
        .map(balance -> this.verifyAmountToPay(balance, payed, paidAmount))
        .map(
            amount -> buildPaymentCharge(groupId, payer, payed, amount))
        .flatMap(pc -> expenseChargeStore.store(pc).thenReturn(pc));
  }

private ImmutableCharge buildPaymentCharge(String groupId, String payer, String payed, Money amount) {
	return ImmutableCharge.builder()
	    .id(idFactory.newId())
	    .groupId(groupId)
	    .creditor(payer)
	    .debtor(payed)
	    .amount(amount)
	    .build();
}

  private Money verifyAmountToPay(Balance balance, String creditorId, Money paidAmount) {
    if (paidAmount.getAmount().compareTo(balance.getDueTo(creditorId).getAmount()) > 0)
      throw  new InvalidAmountError(paidAmount);
    return paidAmount;
  }
}
