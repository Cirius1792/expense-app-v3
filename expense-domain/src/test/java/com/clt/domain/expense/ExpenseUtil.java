package com.clt.domain.expense;

import com.clt.domain.group.User;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class ExpenseUtil {
  public static Expense newExpense(User owner) {
    return ImmutableExpense.builder()
        .id(UUID.randomUUID().toString())
        .owner(owner.getId())
        .groupId(UUID.randomUUID().toString())
        .amount(Money.euros(BigDecimal.valueOf(new Random().nextDouble())))
        .description("Expense - " + new Random().nextInt())
        .build();
  }
}
