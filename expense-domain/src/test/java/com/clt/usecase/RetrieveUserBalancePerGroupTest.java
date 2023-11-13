package com.clt.usecase;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonStore;
import com.clt.domain.group.PersonUtil;
import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ImmutableExpenseCharge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class RetrieveUserBalancePerGroupTest {

  private static Person PERSON = PersonUtil.newPerson();
  private static ExpenseCharge EXPENSE_CHARGE =
      ImmutableExpenseCharge.builder()
          .id("s-id")
          .groupId("g-id")
          .creditor(PersonUtil.newPerson().id())
          .debtor(PERSON.id())
          .expense("e-id")
          .amount(Money.euros("12.4"))
          .build();
  private PersonStore personStore;
  private ExpenseChargeStore expenseChargeStore;
  private RetrieveUserBalancePerGroupUseCase useCase;

  @BeforeEach
  void initMocks() {
    personStore = Mockito.mock(PersonStore.class);
    Mockito.when(personStore.retrieve(PERSON.id())).thenReturn(Mono.just(PERSON));
    expenseChargeStore = Mockito.mock(ExpenseChargeStore.class);
    Mockito.when(expenseChargeStore.retrieveBy(PERSON.id(), EXPENSE_CHARGE.groupId()))
        .thenReturn(Flux.just(EXPENSE_CHARGE));
    useCase = new RetrieveUserBalancePerGroupUseCase(personStore, expenseChargeStore);
  }

  @DisplayName(
      "Given a user A with a charge of X€ in the group Y"
          + "And given that the user A has no credit"
          + "When retrieving the balance of A "
          + "It's balance is -X")
  @Test
  void retrieve_user_balance_test() {
    useCase
        .retrieve(PERSON.id(), EXPENSE_CHARGE.groupId())
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE.amount().negate())
        .verifyComplete();
  }
}
