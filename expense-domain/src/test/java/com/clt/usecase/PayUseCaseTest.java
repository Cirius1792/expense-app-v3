package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.Money;
import com.clt.domain.ledger.Balance;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.InvalidAmountError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PayUseCaseTest {
  public static final String MARK_ID = "markId";
  public static final String BOB_ID = "bobId";
  public static final String GROUP_ID = "groupId";
  public static final Money AMOUNT_TO_PAY = Money.euros("10.00");
  private ExpenseChargeStore paymentChargeStore;
  private Balance mockedBalance;
  private PayUseCase useCase;

  @BeforeEach
  void initMocks() {
    paymentChargeStore = Mockito.mock(ExpenseChargeStore.class);
    Mockito.when(paymentChargeStore.store(Mockito.any())).thenReturn(Mono.empty());
    mockedBalance = Mockito.mock(Balance.class);
    useCase =
        new PayUseCase(
            new UUIDIdFactory(), paymentChargeStore, (userId, groupId) -> Mono.just(mockedBalance));
  }

  @DisplayName(
      """
        Given a debt of X€ of Bob to Mark
        When Bob pays X € to Mark
        Then a PaymentCharge of X is created having Bob as creditor and Mark as debtor
        """)
  @ParameterizedTest
  @ValueSource(strings = {"10.0", "2.99", "10000000000000000000.0"})
  void should_create_a_payment(String tot) {
    Money paidAmount = Money.euros(tot);
    Mockito.when(mockedBalance.getDueTo(MARK_ID)).thenReturn(paidAmount);
    useCase
        .pay(GROUP_ID, BOB_ID, MARK_ID, paidAmount)
        .as(StepVerifier::create)
        .assertNext(
            paymentCharge -> {
              Assertions.assertEquals(paidAmount, paymentCharge.getAmount());
              Assertions.assertEquals(MARK_ID, paymentCharge.getDebtor());
              Assertions.assertEquals(BOB_ID, paymentCharge.getCreditor());
              Assertions.assertEquals(GROUP_ID, paymentCharge.getGroupId());
            })
        .verifyComplete();
  }

  @DisplayName(
      """
          Given a debt of X€ of Bob to Mark
          When Bob tries to pay Y € with Y > X to Mark
          Then an error is returned
          """)
  @Test
  void should_fail_to_pay_more_than_required_test() {
    Money amount = Money.euros("1000.00");
    Mockito.when(mockedBalance.getDueTo(MARK_ID)).thenReturn(Money.euros("50.0"));
    useCase
        .pay(GROUP_ID, BOB_ID, MARK_ID, amount)
        .as(StepVerifier::create)
        .expectError(InvalidAmountError.class)
        .verify();
  }

  @DisplayName(
      """
              When Bob tries to pay a negative amount to Mark
              Then an error is returned
              """)
  @Test
  void should_fail_for_negative_amounts() {
    Money paidAmount = Money.euros(-10);
    Mockito.when(mockedBalance.getDueTo(MARK_ID)).thenReturn(AMOUNT_TO_PAY);
    useCase
        .pay(GROUP_ID, BOB_ID, MARK_ID, paidAmount)
        .as(StepVerifier::create)
        .expectError(InvalidAmountError.class)
        .verify();
  }

  @DisplayName(
      """
        Given a debt of X€ of Bob to Mark
        When a user pays another user
        Then the generated payment charge is stored
""")
  @Test
  void should_store_the_payment_charge() {
    Mockito.when(mockedBalance.getDueTo(MARK_ID)).thenReturn(AMOUNT_TO_PAY);
    useCase
        .pay(GROUP_ID, BOB_ID, MARK_ID, AMOUNT_TO_PAY)
        .as(StepVerifier::create)
        .assertNext(
            paymentCharge ->
                Mockito.verify(paymentChargeStore, Mockito.atLeastOnce()).store(paymentCharge))
        .verifyComplete();
  }
}
