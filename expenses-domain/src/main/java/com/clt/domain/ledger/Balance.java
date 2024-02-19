package com.clt.domain.ledger;

import java.util.ArrayList;
import java.util.List;

import com.clt.domain.expense.Money;

public class Balance {
  private final String owner;
  private List<Charge> charges;

  public Balance(String owner) {
    this.owner = owner;
    charges = new ArrayList<>();
  }

  public Balance(String owner, Iterable<Charge> charges) {
    this.owner = owner;
    this.charges = new ArrayList<>();
    charges.forEach(this::addExpenseCharge);
  }

  public Money balance() {
    return this.credits().minus(this.debits());
  }

  private Money debits() {
    return this.charges.stream()
        .filter(c -> this.owner.equals(c.getDebtor()))
        .map(Charge::getAmount)
        .reduce(Money.euros(0), Money::plus);
  }

  private Money credits() {
    return this.charges.stream()
        .filter(c -> this.owner.equals(c.getCreditor()))
        .map(Charge::getAmount)
        .reduce(Money.euros(0), Money::plus);
  }

  public void addExpenseCharge(Charge charge) {
    if (!this.owner.equals(charge.getCreditor()) && !this.owner.equals(charge.getDebtor()))
      throw new IllegalArgumentException("The balance owner is not the debtor nor the creditor");
    this.charges.add(charge);
  }

  public Money getDueTo(String user) {
    Money debits =
        this.charges.stream()
            .filter(c -> user.equals(c.getCreditor()))
            .map(Charge::getAmount)
            .reduce(Money.euros(0), Money::plus);
    Money credits =
        this.charges.stream()
            .filter(c -> user.equals(c.getDebtor()))
            .map(Charge::getAmount)
            .reduce(Money.euros(0), Money::plus);
    return credits.minus(debits).negate();
  }
}
