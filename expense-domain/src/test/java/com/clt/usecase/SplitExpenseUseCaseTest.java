package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseRecord;
import com.clt.domain.expense.ImmutableExpense;
import com.clt.domain.expense.Money;
import com.clt.domain.group.Group;
import com.clt.domain.group.GroupUtil;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonUtil;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ExpenseSplitter;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class SplitExpenseUseCaseTest {

  private ExpenseChargeStore expenseChargeStore;
  private SplitExpenseUseCase useCase;

  private static final Person AL = PersonUtil.newPerson();
  private static final Person JOHN = PersonUtil.newPerson();
  private static final Person JACK = PersonUtil.newPerson();
  private static final Group GROUP = GroupUtil.newGroup("group", Arrays.asList(AL, JACK, JOHN));

  private Expense buildExpense(String cost, Person owner, Group group) {
    return ImmutableExpense.builder()
        .owner(owner.id())
        .id("x")
        .description("Milk")
        .amount(Money.euros(cost))
        .groupId(group.id())
        .build();
  }

  @BeforeEach
  void initMocks() {
    expenseChargeStore = Mockito.mock(ExpenseChargeStore.class);
    Mockito.when(expenseChargeStore.store(Mockito.any()))
        .thenAnswer(args -> Mono.just(args.getArgument(0)));
    useCase = new SplitExpenseUseCase(new ExpenseSplitter(new UUIDIdFactory()), expenseChargeStore);
  }

  @DisplayName(
      "Given a group made of three people: Al, John and Jack"
          + "And given that Al bought the milk for X€"
          + "When splitting the expense within the group "
          + "Then an expense charge of X/3€ from Jack to Al is created"
          + "And an expense charge of X/3€ from John to Al is created")
  @ParameterizedTest
  @CsvSource({"6,2.00,2.00", "8,2.67,2.67", "4, 1.34, 1.34"})
  void split_expense_test(String cost, String dueAmount1, String dueAmount2) {
    Expense expense = buildExpense(cost, AL, GROUP);

    useCase
        .split(new ExpenseRecord(AL, expense, GROUP))
        .as(StepVerifier::create)
        .assertNext(
            split -> {
              Assertions.assertEquals(split.debtor(), JACK.id());
              Assertions.assertEquals(Money.euros(new BigDecimal(dueAmount1)), split.amount());
              Assertions.assertEquals(AL.id(), split.creditor());
            })
        .assertNext(
            split -> {
              Assertions.assertEquals(JOHN.id(), split.debtor());
              Assertions.assertEquals(Money.euros(new BigDecimal(dueAmount2)), split.amount());
              Assertions.assertEquals(AL.id(), split.creditor());
            })
        .verifyComplete();
  }

  @DisplayName(
      "Given a valid expense "
          + "When the expense is split between the members of the group "
          + "Then the Expense Charges are stored ")
  @Test
  void store_expense_charge_test() {
    Expense expense = buildExpense("20", AL, GROUP);
    useCase
        .split(new ExpenseRecord(AL, expense, GROUP))
        .as(StepVerifier::create)
        .assertNext(split -> Mockito.verify(expenseChargeStore, Mockito.times(1)).store(split))
        .assertNext(split -> Mockito.verify(expenseChargeStore, Mockito.times(1)).store(split))
        .verifyComplete();
  }
}
