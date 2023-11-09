package com.clt.usecase;

import com.clt.domain.commons.Page;
import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.expense.ExpenseUtil;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonStore;
import com.clt.domain.group.PersonUtil;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.ExpenseAggregateFactory;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FindExpensesPerGroupUseCaseTest {

  private static final String GROUP_ID = "gid";
  private static final Person EXPENSE_OWNER = PersonUtil.newPerson();
  private static final List<Expense> EXPENSES =
      IntStream.range(0, 10).mapToObj($ -> ExpenseUtil.newExpense(EXPENSE_OWNER)).toList();

  private static final List<ExpenseAggregate> EXPECTED =
      EXPENSES.stream().map(e -> ExpenseAggregateFactory.fromDomain(e, EXPENSE_OWNER)).toList();

  private static FindExpensesPerGroupUseCase useCase;

  @BeforeAll
  public static void initMocks() {
    PersonStore personStore = Mockito.mock(PersonStore.class);
    Mockito.when(personStore.retrieve(EXPENSE_OWNER.id())).thenReturn(Mono.just(EXPENSE_OWNER));

    ExpenseStore expenseStore = Mockito.mock(ExpenseStore.class);
    Mockito.when(expenseStore.retrieveByGroup(Mockito.eq(GROUP_ID), Mockito.any()))
        .thenAnswer(
            args -> {
              Page p = args.getArgument(1);
              return Flux.fromIterable(EXPENSES.subList(p.startFrom(), p.endAt()));
            });
    useCase = new FindExpensesPerGroupUseCase(expenseStore, personStore);
  }

  @DisplayName(
      "Given a list of expenses "
          + "When retrieving the expenses for a given group"
          + "Then the expenses are returned organised in pages"
          + "And they are sorted by creation date")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 5, 7})
  void retrieve_expenses_with_pagination_test(int pageSize) {
    int expectedFirstPageSize = Math.min(EXPECTED.size(), pageSize);
    useCase
        .retrieve(GROUP_ID, 1, pageSize)
        .as(StepVerifier::create)
        .expectNextSequence(EXPECTED.subList(0, expectedFirstPageSize))
        .verifyComplete();
  }
}
