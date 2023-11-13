package com.clt.usecase;

import com.clt.domain.expense.Money;
import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ImmutableExpenseCharge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class RetrieveSplitPerExpenseTest {

  private static final String EXPENSE = "e1";
  private static final ExpenseCharge EXPENSE_CHARGE_1 =
      ImmutableExpenseCharge.builder()
          .id("ec-1")
          .expense(EXPENSE)
          .groupId("g1")
          .amount(Money.euros("0.50"))
          .debtor("s1")
          .creditor("s2")
          .build();

  private static final ExpenseCharge EXPENSE_CHARGE_2 =
      ImmutableExpenseCharge.builder()
          .id("ec-2")
          .expense(EXPENSE)
          .groupId("g1")
          .amount(Money.euros("4.50"))
          .debtor("s1")
          .creditor("s2")
          .build();
  private static ExpenseChargeStore expenseChargeStore;
  private static RetrieveSplitPerExpenseUseCase useCase;

  @BeforeAll
  static void initMocks() {
    expenseChargeStore = Mockito.mock(ExpenseChargeStore.class);
    Mockito.when(expenseChargeStore.retrieveBy(EXPENSE))
        .thenReturn(Flux.just(EXPENSE_CHARGE_1, EXPENSE_CHARGE_2));
    useCase = new RetrieveSplitPerExpenseUseCase(expenseChargeStore);
  }

  @DisplayName(
      "Given an expense e "
          + "And given two expense charge generated to split the expense e "
          + "When retrieving the split result for e "
          + "Then the two generated expense charge are returned")
  @Test
  void retrieve_expense_charge_per_expense_test() {
    useCase
        .retrieve(EXPENSE)
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE_1)
        .expectNext(EXPENSE_CHARGE_2)
        .verifyComplete();
  }
}
