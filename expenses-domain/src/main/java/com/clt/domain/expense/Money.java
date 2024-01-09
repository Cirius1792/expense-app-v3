package com.clt.domain.expense;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableMoney.class)
@JsonDeserialize(as = ImmutableMoney.class)
public abstract class Money {
  private static final int SCALE = 2;

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

  public Money divide(BigDecimal amount) {
    return Money.euros(getAmount().divide(amount, 2, RoundingMode.CEILING));
  }
  public Money times(Money amount) {
    return Money.euros(getAmount().multiply(amount.getAmount()));
  }

  public static Money euros(double amount) {
    return ImmutableMoney.builder()
        .amount(BigDecimal.valueOf(amount).setScale(SCALE, RoundingMode.CEILING))
        .build();
  }

  public static Money euros(BigDecimal amount) {
    return ImmutableMoney.builder().amount(amount.setScale(SCALE, RoundingMode.CEILING)).build();
  }

  public static Money euros(String amount) {
    return ImmutableMoney.euros(new BigDecimal(amount).setScale(SCALE, RoundingMode.CEILING));
  }

  public static Money copyOf(Money money) {
    return ImmutableMoney.copyOf(money);
  }
}
