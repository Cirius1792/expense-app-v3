package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.group.User;

import java.util.ArrayList;
import java.util.List;

public class Balance {
  private final User owner;
  private List<ExpenseCharge> charges;

  public Balance(User owner) {
    this.owner = owner;
    charges = new ArrayList<>();
  }

  public Balance(User owner, Iterable<ExpenseCharge> charges) {
    this.owner = owner;
    this.charges = new ArrayList<>();
    charges.forEach(this::addExpenseCharge);
  }

  public Money balance() {
    return this.credits().minus(this.debits());
  }

  private Money debits() {
    return this.charges.stream()
        .filter(c -> this.owner.getId().equals(c.getDebtor()))
        .map(ExpenseCharge::getAmount)
        .reduce(Money.euros(0), Money::plus);
  }

  private Money credits() {
    return this.charges.stream()
        .filter(c -> this.owner.getId().equals(c.getCreditor()))
        .map(ExpenseCharge::getAmount)
        .reduce(Money.euros(0), Money::plus);
  }

  public void addExpenseCharge(ExpenseCharge charge) {
    if (!this.owner.getId().equals(charge.getCreditor()) && !this.owner.getId().equals(charge.getDebtor()))
      throw new IllegalArgumentException("The balance owner is not the debtor nor the creditor");
    this.charges.add(charge);
  }

  public Money getDueTo(User user) {
    Money debits =
        this.charges.stream()
            .filter(c -> user.getId().equals(c.getCreditor()))
            .map(ExpenseCharge::getAmount)
            .reduce(Money.euros(0), Money::plus);
    Money credits =
        this.charges.stream()
            .filter(c -> user.getId().equals(c.getDebtor()))
            .map(ExpenseCharge::getAmount)
            .reduce(Money.euros(0), Money::plus);
    return credits.minus(debits).negate();
  }
}
