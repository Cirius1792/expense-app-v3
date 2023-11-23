package com.clt.domain.ledger;

import com.clt.domain.expense.Money;

public class InvalidAmountError extends RuntimeException {
    public InvalidAmountError(Money amount){
        super("The amount %s is not valid".formatted(amount));
    }
}
