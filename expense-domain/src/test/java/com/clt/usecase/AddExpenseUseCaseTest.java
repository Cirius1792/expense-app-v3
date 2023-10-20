package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.expense.Money;
import com.clt.domain.group.*;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class AddExpenseUseCaseTest {

  private static final String DESCRIPTION = "Milk";
  private static final Money AMOUNT = Money.euros(10);
  private static final Person OWNER = PersonUtil.newPerson();
  private static final Group GROUP = GroupUtil.newGroup("gid", Arrays.asList(OWNER));
  private PersonStore personStore;
  private GroupStore groupStore;
  private ExpenseStore expenseStore;
  private AddExpenseUseCase useCase;

  @BeforeEach
  void initMocks() {
    personStore = Mockito.mock(PersonStore.class);
    Mockito.when(personStore.retrieve(OWNER.id())).thenReturn(Mono.just(OWNER));

    groupStore = Mockito.mock(GroupStore.class);
    Mockito.when(groupStore.retrieve(GROUP.id())).thenReturn(Mono.just(GROUP));

    expenseStore = Mockito.mock(ExpenseStore.class);
    Mockito.when(expenseStore.store(Mockito.any()))
        .thenAnswer(args -> Mono.just(args.getArgument(0)));

    useCase =
        new AddExpenseUseCase(
            personStore, groupStore, new ExpenseFactory(new UUIDIdFactory()), expenseStore);
  }

  @DisplayName(
      "Given a valid person"
          + "And a valid group "
          + "When registering a new expense "
          + "The the new expense is created "
          + "And the new expense is tored")
  @Test
  void test_create_and_store_new_expense() {
    Mono<ExpenseAggregate> producer = useCase.create(DESCRIPTION, AMOUNT, OWNER.id(), GROUP.id());
    StepVerifier.create(producer)
        .assertNext(
            actual -> {
              Assertions.assertNotNull(actual);
              Assertions.assertEquals(DESCRIPTION, actual.description());
              Assertions.assertEquals(AMOUNT, actual.amount());
              Mockito.verify(expenseStore, Mockito.atLeastOnce()).store(Mockito.any());
            })
        .verifyComplete();
  }

  @DisplayName(
      "Given an invalid owner "
          + "When creating an expense "
          + "Then a PersonNotFoundError is thrown")
  @Test
  void invalid_owner_test() {
    String invalidOwnerId = "not-existent-id";
    Mockito.when(personStore.retrieve(invalidOwnerId)).thenReturn(Mono.empty());
    var producer = useCase.create(DESCRIPTION, AMOUNT, invalidOwnerId, GROUP.id());
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
    var producer = useCase.create(DESCRIPTION, AMOUNT, OWNER.id(), invalidGroupId);
    StepVerifier.create(producer).verifyError(GroupNotFound.class);
  }
}
