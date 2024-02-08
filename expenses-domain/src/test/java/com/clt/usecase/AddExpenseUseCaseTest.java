package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.*;
import com.clt.domain.group.*;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.event.Observer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class AddExpenseUseCaseTest {

  private static final String DESCRIPTION = "Milk";
  private static final Money AMOUNT = Money.euros(10);
  private static final User OWNER = PersonUtil.newPerson();
  private static final Group GROUP = GroupUtil.newGroup("gid", List.of(OWNER));
  private UserStore personStore;
  private GroupStore groupStore;
  private ExpenseStore expenseStore;
  private AddExpenseUseCase useCase;
  private Observer<ExpenseRecord> newExpenseNotifier;

  @BeforeEach
  void initMocks() {
    personStore = Mockito.mock(UserStore.class);
    Mockito.when(personStore.retrieve(OWNER.getId())).thenReturn(Mono.just(OWNER));

    groupStore = Mockito.mock(GroupStore.class);
    Mockito.when(groupStore.retrieve(GROUP.getId())).thenReturn(Mono.just(GROUP));

    expenseStore = Mockito.mock(ExpenseStore.class);
    Mockito.when(expenseStore.store(Mockito.any())).thenReturn(Mono.empty());
    newExpenseNotifier = Mockito.spy(new NewExpenseNotifierNop());

    useCase =
        new AddExpenseUseCase(
            personStore,
            groupStore,
            new ExpenseFactory(new UUIDIdFactory()),
            expenseStore,
            newExpenseNotifier);
  }

  @DisplayName(
      """
                       	       	Given a valid user
                            And a valid group
                            When registering a new expense
                            The the new expense is created
                            And the new expense is stored
                    """)
  @Test
  void test_create_and_store_new_expense() {
    Mono<ExpenseAggregate> producer =
        useCase.create(DESCRIPTION, AMOUNT, OWNER.getId(), GROUP.getId());
    StepVerifier.create(producer)
        .assertNext(
            actual -> {
              Assertions.assertNotNull(actual);
              Assertions.assertEquals(DESCRIPTION, actual.getDescription());
              Assertions.assertEquals(AMOUNT, actual.getAmount());
              Mockito.verify(expenseStore, Mockito.atLeastOnce()).store(Mockito.any());
            })
        .verifyComplete();
  }

  @DisplayName(
      """
                       		Given an invalid owner
                           	When creating an expense
                           	Then a PersonNotFoundError is thrown
                    """)
  @Test
  void invalid_owner_test() {
    String invalidOwnerId = "not-existent-id";
    Mockito.when(personStore.retrieve(invalidOwnerId)).thenReturn(Mono.empty());
    var producer = useCase.create(DESCRIPTION, AMOUNT, invalidOwnerId, GROUP.getId());
    StepVerifier.create(producer).verifyError(PersonNotFound.class);
  }

  @DisplayName(
      "Given an invalid group "
          + "When creating an expense "
          + "Then a GroupNotFoundError is thrown")
  @Test
  void invalid_group_test() {
    String invalidGroupId = "not-existent-id";
    Mockito.when(groupStore.retrieve(invalidGroupId)).thenReturn(Mono.empty());
    var producer = useCase.create(DESCRIPTION, AMOUNT, OWNER.getId(), invalidGroupId);
    StepVerifier.create(producer).verifyError(GroupNotFound.class);
  }

  @DisplayName("When a new expense is stored " + "Then the new_expense_created event is notified")
  @Test
  void notify_expense_creation_on_store() {
    useCase
        .create(DESCRIPTION, AMOUNT, OWNER.getId(), GROUP.getId())
        .as(StepVerifier::create)
        .assertNext(
            e -> Mockito.verify(newExpenseNotifier, Mockito.atLeastOnce()).notify(Mockito.any()))
        .verifyComplete();
  }

  @DisplayName(
      """
                    When a new expense can't be stored for any reason
                    Then the new_expense_created event is not notified
                   """)
  @Test
  void not_notify_on_expense_store_failure() {
    Mockito.when(expenseStore.store(Mockito.any())).thenReturn(Mono.error(new RuntimeException()));
    useCase
        .create(DESCRIPTION, AMOUNT, OWNER.getId(), GROUP.getId())
        .as(StepVerifier::create)
        .expectError()
        .verify();
    Mockito.verify(newExpenseNotifier, Mockito.never()).notify(Mockito.any());
  }

  @DisplayName(
      """
            When the notification of the new expense fails
            Then no new expense should be created
            And an error should be returned
            """)
  @Test
  void should_fail_on_notification_fail() {
    Mockito.when(newExpenseNotifier.notify(Mockito.any())).thenThrow(new RuntimeException());
    useCase
        .create(DESCRIPTION, AMOUNT, OWNER.getId(), GROUP.getId())
        .as(StepVerifier::create)
        .expectError()
        .verify();
  }
}
