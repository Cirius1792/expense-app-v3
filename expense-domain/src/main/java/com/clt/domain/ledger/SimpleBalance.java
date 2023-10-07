package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;

import java.util.HashMap;
import java.util.Map;

public class SimpleBalance implements Balance{
    private Map<Person, Money> balance = new HashMap<>();
    private Map<String, ExpenseCharge> evaluated = new HashMap<>();
    public void addExpense(ExpenseCharge expenseCharge){
        if(evaluated.containsKey(expenseCharge.id()))
            throw new IllegalArgumentException("Already evaluated");
        balance.compute(expenseCharge.creditor(), (k,v) -> v == null ? expenseCharge.dueAmount().negate() : v.plus(expenseCharge.dueAmount().negate()) );
        balance.compute(expenseCharge.debtor(), (k,v) -> v == null ? expenseCharge.dueAmount() : v.plus(expenseCharge.dueAmount()));
    }
    @Override
    public Map<Person, Money> owned(Person debtor) {
        return null;
    }
}
