package com.clt.usecase;

import com.clt.domain.expense.*;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonStore;
import com.clt.domain.group.PersonUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FindExpenseUseCaseTest {

    private static final String EXPENSE_ID = "e-id";
    private static final String NOT_VALID_EXPENSE_ID = "not-an-id";
    private static final Person EXPENSE_OWNER = PersonUtil.newPerson();
    private static final String GROUP_ID = "g-id";
    private static final Money EXPENSE_AMOUNT = Money.euros("12.50");
    private static final String EXPENSE_DESCRIPTION = "Milk";
    private static final Expense EXPENSE = ImmutableExpense.builder()
            .id(EXPENSE_ID)
            .groupId(GROUP_ID)
            .amount(EXPENSE_AMOUNT)
            .description(EXPENSE_DESCRIPTION)
            .owner(EXPENSE_OWNER.id())
            .build();
    private static final ExpenseAggregate EXPENSE_AGGREGATE = ImmutableExpenseAggregate.builder()
            .id(EXPENSE_ID)
            .groupId(GROUP_ID)
            .amount(EXPENSE_AMOUNT)
            .description(EXPENSE_DESCRIPTION)
            .owner(EXPENSE_OWNER)
            .build();
    private static FindExpenseUseCase useCase;
    @BeforeAll
    static void initMocks(){
        PersonStore personStore = Mockito.mock(PersonStore.class);
        Mockito.when(personStore.retrieve(EXPENSE_OWNER.id()))
                .thenReturn(Mono.just(EXPENSE_OWNER));

        ExpenseStore expenseStore = Mockito.mock(ExpenseStore.class);
        Mockito.when(expenseStore.retrieve(EXPENSE_ID))
                .thenReturn(Mono.just(EXPENSE));
        Mockito.when(expenseStore.retrieve(NOT_VALID_EXPENSE_ID))
                .thenReturn(Mono.empty());

        useCase = new FindExpenseUseCase(expenseStore, personStore);

    }
    @DisplayName("Given an existing expense " +
            "When looking for the expense" +
            "Then the expense and the person who made it are returned")
    @Test
    void retrieve_expense_test(){
        useCase.retrieveExpense(EXPENSE_ID)
                .as(StepVerifier::create)
                .expectNext(EXPENSE_AGGREGATE)
                .verifyComplete();
    }

    @DisplayName("Given the id of a non existing expense " +
            "When looking for the expense " +
            "Then an ExpenseNotFoundError is thrown")
    @Test
    void retrieve_not_existing_expense_test(){
        useCase.retrieveExpense("not-an-id")
                .as(StepVerifier::create)
                .verifyError(ExpenseNotFound.class);
    }


}