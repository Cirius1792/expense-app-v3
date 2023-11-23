package com.clt.usecase;

import com.clt.domain.expense.Money;
import com.clt.domain.group.User;
import com.clt.domain.group.UserStore;
import com.clt.domain.group.PersonUtil;
import com.clt.domain.ledger.Charge;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ImmutableCharge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class RetrieveUserBalancePerGroupTest {

  private static User User = PersonUtil.newPerson();
  private static Charge EXPENSE_CHARGE =
      ImmutableCharge.builder()
          .id("s-id")
          .groupId("g-id")
          .creditor(PersonUtil.newPerson().getId())
          .debtor(User.getId())
          .expense("e-id")
          .amount(Money.euros("12.4"))
          .build();
  private UserStore personStore;
  private ExpenseChargeStore expenseChargeStore;
  private RetrieveUserBalancePerGroupUseCase useCase;

  @BeforeEach
  void initMocks() {
    personStore = Mockito.mock(UserStore.class);
    Mockito.when(personStore.retrieve(User.getId())).thenReturn(Mono.just(User));
    expenseChargeStore = Mockito.mock(ExpenseChargeStore.class);
    Mockito.when(expenseChargeStore.retrieveBy(User.getId(), EXPENSE_CHARGE.getGroupId()))
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
        .retrieve(User.getId(), EXPENSE_CHARGE.getGroupId())
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE.getAmount().negate())
        .verifyComplete();
  }
}
