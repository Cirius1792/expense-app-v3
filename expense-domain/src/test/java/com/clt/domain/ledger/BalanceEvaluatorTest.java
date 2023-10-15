package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BalanceEvaluatorTest {

  private static final Person ALICE = PersonUtil.newPerson();
  private static final Person BOB = PersonUtil.newPerson();
  private static final Person SARA = PersonUtil.newPerson();
  private static final ExpenseCharge EXPENSE_CHARGE_FROM_ALICE_TO_BOB =
      ImmutableExpenseCharge.builder()
          .id("EXPENSE_CHARGE_FROM_ALICE_TO_BOB")
          .debtor(BOB)
          .creditor(ALICE)
          .expense("e1")
          .groupId("g1")
          .dueAmount(Money.euros(2))
          .build();
  private static final ExpenseCharge EXPENSE_CHARGE_FROM_BOB_TO_ALICE =
      ImmutableExpenseCharge.builder()
          .id("EXPENSE_CHARGE_FROM_BOB_TO_ALICE")
          .creditor(BOB)
          .debtor(ALICE)
          .expense("e2")
          .groupId("g1")
          .dueAmount(Money.euros(1))
          .build();

  private static final ExpenseCharge EXPENSE_CHARGE_FROM_BOB_TO_ALICE_2 =
      ImmutableExpenseCharge.builder()
          .id("EXPENSE_CHARGE_FROM_BOB_TO_ALICE_2")
          .creditor(BOB)
          .debtor(ALICE)
          .expense("e3")
          .groupId("g1")
          .dueAmount(Money.euros(6))
          .build();
  private static final ExpenseCharge EXPENSE_CHARGE_FROM_SARA_TO_BOB =
      ImmutableExpenseCharge.builder()
          .id("EXPENSE_CHARGE_FROM_SARA_TO_BOB")
          .creditor(SARA)
          .debtor(BOB)
          .expense("e4")
          .groupId("g1")
          .dueAmount(Money.euros(3))
          .build();

  @DisplayName(
      "Given the balance of BOB "
          + "And an Expense charge of 2€ from ALICE "
          + "When the expense is added to the balance "
          + "Then the debits are of 2€")
  @Test
  void add_one_expense_test() {
    Balance balance = new Balance(BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_ALICE_TO_BOB);
    Assertions.assertEquals(
        EXPENSE_CHARGE_FROM_ALICE_TO_BOB.dueAmount().negate(), balance.balance());
  }

  @DisplayName(
      "Given the balance of BOB "
          + "And an Expense charge of 2€ from ALICE "
          + "And an Expense charge of 1€ from BOB "
          + "When the expense is added to the balance "
          + "Then the debits are of 1€ ")
  @Test
  void add_two_expense_test() {
    Balance balance = new Balance(BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_ALICE_TO_BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_BOB_TO_ALICE);

    Assertions.assertEquals(Money.euros(-1), balance.balance());
  }

  @DisplayName(
      "Given the balance of BOB "
          + "And an Expense charge of 2€ from ALICE "
          + "And an Expense charge of 6€ from BOB "
          + "And an expense charge of 3€ from SARA "
          + "When the expense is added to the balance "
          + "Then the credits are of 1€")
  @Test
  void add_three_expenses_test() {
    Balance balance = new Balance(BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_ALICE_TO_BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_BOB_TO_ALICE_2);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_SARA_TO_BOB);

    Assertions.assertEquals(Money.euros(1), balance.balance());
  }

  @DisplayName(
      "Given the balance of BOB "
          + "And an Expense charge of 2€ from ALICE "
          + "And an Expense charge of 6€ from BOB to ALICE "
          + "And an expense charge of 3€ from SARA "
          + "When the expense is added to the balance "
          + "Then 4€ are due from ALICE to BOB "
          + "And 3€ are due from BOB to SARA")
  @Test
  void test_balance_per_person() {
    Balance balance = new Balance(BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_ALICE_TO_BOB);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_BOB_TO_ALICE_2);
    balance.addExpenseCharge(EXPENSE_CHARGE_FROM_SARA_TO_BOB);

    Assertions.assertEquals(Money.euros(-4), balance.getDueTo(ALICE));
    Assertions.assertEquals(Money.euros(3), balance.getDueTo(SARA));
  }
}
