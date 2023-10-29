package com.clt.expenses.group.response;

import com.clt.expenses.expense.request.MoneyDto;

public class BalanceResponse {
    private MoneyDto balance;

    public BalanceResponse(MoneyDto balance) {
        this.balance = balance;
    }

    public BalanceResponse() {
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "balance=" + balance +
                '}';
    }

    public MoneyDto getBalance() {
        return balance;
    }

    public void setBalance(MoneyDto balance) {
        this.balance = balance;
    }
}
