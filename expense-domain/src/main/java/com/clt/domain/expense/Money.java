package com.clt.domain.expense;

import org.immutables.value.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value.Immutable
public abstract class Money {

    public abstract BigDecimal getAmount();

    public Money plus(Money money) {
        return Money.euros(getAmount().add(money.getAmount()));
    }

    public Money negate() {
        return Money.euros(this.getAmount().negate());
    }

    public Money minus(Money money) {
        return Money.euros(getAmount().subtract(money.getAmount()));
    }

    public Money divide(Money money) {
        return Money.euros(getAmount().divide(money.getAmount(), 2, RoundingMode.CEILING));
    }

    public Money divide(BigDecimal amount) {
        return Money.euros(getAmount().divide(amount, 2, RoundingMode.CEILING));
    }

    public static Money euros(double amount) {
        return ImmutableMoney.builder().amount(BigDecimal.valueOf(amount)).build();
    }

    public static Money euros(BigDecimal amount) {
        return ImmutableMoney.builder().amount(amount).build();
    }

    public static Money euros(String amount) {
        return ImmutableMoney.euros(new BigDecimal(amount));
    }

    public static Money copyOf(Money money) {
        return ImmutableMoney.copyOf(money);
    }
}
