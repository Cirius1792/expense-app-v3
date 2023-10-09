package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;

import java.util.ArrayList;
import java.util.List;

public class Balance {
    private final Person owner;
    private List<ExpenseCharge> charges;

    public Balance(Person owner) {
        this.owner = owner;
        charges = new ArrayList<>();
    }

    public Money balance(){
        return this.credits().minus(this.debits());
    }
    private Money debits() {
        return this.charges.stream()
                .filter(c -> this.owner.id().equals(c.debtor().id()))
                .map(ExpenseCharge::dueAmount)
                .reduce(Money.euros(0), Money::plus);
    }

    private Money credits() {
        return this.charges.stream()
                .filter(c -> this.owner.id().equals(c.creditor().id()))
                .map(ExpenseCharge::dueAmount)
                .reduce(Money.euros(0), Money::plus);

    }

    public void addExpenseCharge(ExpenseCharge charge) {
        if (!this.owner.id().equals(charge.creditor().id())
                && !this.owner.id().equals(charge.debtor().id()))
            throw new IllegalArgumentException("The balance owner is not the debtor nor the creditor");
        this.charges.add(charge);
    }

    public List<ExpenseCharge> getExpenseCharges() {
        return new ArrayList<>(this.charges);
    }

    public Money getDueTo(Person person) {
        Money debits = this.charges.stream()
                .filter(c -> person.id().equals(c.creditor().id()))
                .map(ExpenseCharge::dueAmount)
                .reduce(Money.euros(0), Money::plus);
        Money credits = this.charges.stream()
                .filter(c -> person.id().equals(c.debtor().id()))
                .map(ExpenseCharge::dueAmount)
                .reduce(Money.euros(0), Money::plus);
        return credits.minus(debits).negate();
    }
}
