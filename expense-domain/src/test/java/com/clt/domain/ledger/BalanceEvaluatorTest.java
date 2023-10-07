package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BalanceEvaluatorTest {

    private static final Person ALICE = PersonUtil.newPerson();
    private static final Person BOB = PersonUtil.newPerson();
    private static final Person SARA = PersonUtil.newPerson();
    private static final ExpenseCharge EC11 =
            ImmutableExpenseCharge.builder()
                    .id("ec11")
                    .debtor(BOB)
                    .creditor(ALICE)
                    .expense("e1")
                    .groupId("g1")
                    .dueAmount(Money.euros(1))
                    .build();
    private static final ExpenseCharge EC12 =
            ImmutableExpenseCharge.builder()
                    .id("ec12")
                    .debtor(SARA)
                    .creditor(ALICE)
                    .expense("e1")
                    .groupId("g1")
                    .dueAmount(Money.euros(1))
                    .build();

   private static final ExpenseCharge EC21 =
            ImmutableExpenseCharge.builder()
                    .id("ec21")
                    .debtor(ALICE)
                    .creditor(BOB)
                    .expense("e2")
                    .groupId("g1")
                    .dueAmount(Money.euros(2.94))
                    .build();
    private static final ExpenseCharge EC22 =
            ImmutableExpenseCharge.builder()
                    .id("ec22")
                    .debtor(SARA)
                    .creditor(BOB)
                    .expense("e2")
                    .groupId("g1")
                    .dueAmount(Money.euros(2.94))
                    .build();

   private static final ExpenseCharge EC31 =
            ImmutableExpenseCharge.builder()
                    .id("ec31")
                    .debtor(ALICE)
                    .creditor(SARA)
                    .expense("e3")
                    .groupId("g1")
                    .dueAmount(Money.euros(1.15))
                    .build();
    private static final ExpenseCharge EC32 =
            ImmutableExpenseCharge.builder()
                    .id("ec32")
                    .debtor(BOB)
                    .creditor(SARA)
                    .expense("e3")
                    .groupId("g1")
                    .dueAmount(Money.euros(1.15))
                    .build();
    private static final List<ExpenseCharge> CHARGES =
            Arrays.asList(
                    EC11, EC12,
                    EC21, EC22,
                    EC31, EC32);

    BalanceEvaluator balanceEvaluator = new BalanceEvaluator();

    @DisplayName(
            "Given a group of 3 people"
                    + "And a list of expenses made by those people "
                    + "When evaluating the balance "
                    + "Then each person has a balance")
    @Test
    void balance_evaluation_test() {
        Balance actual = balanceEvaluator.evaluate(CHARGES);
        Money ownedByBob = EC32.dueAmount().plus(EC11.dueAmount());
        Assertions.assertEquals(ownedByBob, actual.totalOwned(BOB));
    }
}
