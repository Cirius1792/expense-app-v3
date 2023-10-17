package com.clt.domain.ledger;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ImmutableExpense;
import com.clt.domain.expense.Money;
import com.clt.domain.group.Group;
import com.clt.domain.group.GroupUtil;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExpenseSplitterTest {

  private static final Person AL = PersonUtil.newPerson();
  private static final Person JOHN = PersonUtil.newPerson();
  private static final Person JACK = PersonUtil.newPerson();
  private static final Group GROUP = GroupUtil.newGroup("group", Arrays.asList(AL, JACK, JOHN));
  ExpenseSplitter splitter = new ExpenseSplitter(new UUIDIdFactory());

  @DisplayName(
      "Given a group made of three people: Al, John and Jack"
          + "And given that Al bought the milk for X€"
          + "When splitting the expense within the group "
          + "Then an expense charge of X/3€ from Jack to Al is created"
          + "And an expense charge of X/3€ from John to Al is created")
  @ParameterizedTest
  @CsvSource({"6,2.00,2.00", "8,2.67,2.67", "4, 1.34, 1.34"})
  void split_expense_test(String cost, String dueAmount1, String dueAmount2) {
    Expense expense =
        ImmutableExpense.builder()
            .owner(AL.id())
            .id("x")
            .description("Milk")
            .amount(Money.euros(cost))
            .groupId(GROUP.id())
            .build();
    List<ExpenseCharge> actual = splitter.split(expense, GROUP);
    ExpenseCharge split =
        actual.stream().filter(el -> JACK.id().equals(el.debtor())).findAny().orElseThrow();
    Assertions.assertEquals(Money.euros(new BigDecimal(dueAmount1)), split.dueAmount());
    Assertions.assertEquals(AL.id(), split.creditor());

    split = actual.stream().filter(el -> JOHN.id().equals(el.debtor())).findAny().orElseThrow();
    Assertions.assertEquals(Money.euros(new BigDecimal(dueAmount2)), split.dueAmount());
    Assertions.assertEquals(AL.id(), split.creditor());

    Assertions.assertEquals(2, actual.size());
  }
}
