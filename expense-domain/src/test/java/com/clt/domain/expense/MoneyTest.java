package com.clt.domain.expense;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {
  @Test
  @DisplayName("Should create money")
  void testMoney() {
    Money five = Money.euros(5);
    Assertions.assertEquals(Money.euros(5), five);

    Money six = Money.euros(6);
    Assertions.assertNotEquals(five, six);
  }

  @Test
  @DisplayName("Should add money")
  void testPlusOp() {
    Money five = Money.euros(5);
    Money six = Money.euros(6);
    Assertions.assertEquals(Money.euros(11), six.plus(five));
    Assertions.assertEquals(Money.euros(6), six);
    Assertions.assertEquals(Money.euros(5), five);
  }

  @Test
  @DisplayName("Should subtract money")
  void testMinusOp() {
    Money five = Money.euros(5);
    Money six = Money.euros(6);
    Assertions.assertEquals(Money.euros(1), six.minus(five));
  }
}
