package com.clt.expenses.domain.expense;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.expense.ImmutableExpense;
import com.clt.domain.expense.Money;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@SpringBootTest(classes = StoreTestConfiguration.class)
class ExpenseStoreImplTest {

  @Autowired DatabaseClient database;
  @Autowired ExpenseStore expenseStore;
  @Autowired ExpenseRepository expenseRepository;

  @BeforeEach
  void setUp() {

    Hooks.onOperatorDebug();
    var statements =
        Arrays.asList( //
            "DROP TABLE IF EXISTS expense;",
            "CREATE TABLE expense ( id VARCHAR PRIMARY KEY, "
                + "description VARCHAR(255), "
                + "amount VARCHAR(65) NOT NULL, "
                + "owner_id VARCHAR(100) NOT NULL, "
                + "group_id VARCHAR(100) NOT NULL"
                + ");");

    statements.forEach(
        it ->
            database
                .sql(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());
  }

  private static final String EXPENSE_ID = "id";
  private static final String DESCRIPTION = "my expense";
  private static final String AMOUNT = "12.20";
  private static final String GROUP_ID = "g-id";
  private static final String OWNER_ID = "own-id";

  private ExpenseEntity buildExpenseEntity() {
    ExpenseEntity entity = new ExpenseEntity();
    entity.setId(EXPENSE_ID);
    entity.setGroupId(GROUP_ID);
    entity.setDescription(DESCRIPTION);
    entity.setAmount(AMOUNT);
    entity.setOwnerId(OWNER_ID);
    return entity;
  }

  private ExpenseEntity buildNewExpenseEntity() {
    ExpenseEntity entity = new ExpenseEntity();
    entity.setId(UUID.randomUUID().toString());
    entity.setGroupId(GROUP_ID);
    entity.setDescription(DESCRIPTION);
    entity.setAmount(AMOUNT);
    entity.setOwnerId(OWNER_ID);
    return entity;
  }

  private Expense buildExpenseDomain() {
    return ImmutableExpense.builder()
        .id(EXPENSE_ID)
        .description(DESCRIPTION)
        .amount(Money.euros(AMOUNT))
        .groupId(GROUP_ID)
        .owner(OWNER_ID)
        .build();
  }

  @DisplayName("Should save an expense")
  @Test
  void store_expense_test() {
    Expense expense = buildExpenseDomain();
    ExpenseEntity entity = buildExpenseEntity();
    expenseStore.store(expense).as(StepVerifier::create).expectNext(expense).verifyComplete();

    expenseRepository
        .findById(EXPENSE_ID)
        .as(StepVerifier::create)
        .expectNext(entity)
        .verifyComplete();
  }

  @DisplayName("Should retrieve an existing expense by id")
  @Test
  void test_retrieve_expense() {
    ExpenseEntity entity = buildExpenseEntity();
    Expense expense = buildExpenseDomain();
    expenseRepository.save(entity).as(StepVerifier::create).expectNextCount(1).verifyComplete();
    expenseStore.retrieve(EXPENSE_ID).as(StepVerifier::create).expectNext(expense).verifyComplete();
  }

  @DisplayName("Should return paged expenses ")
  @ParameterizedTest
  @ValueSource(ints = {2, 3, 5, 7})
  void test_retrieve_expenses_by_page(int numElements) {
    IntStream.range(0, numElements * 3)
        .forEach(
            $ ->
                expenseRepository
                    .save(buildNewExpenseEntity())
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete());
    expenseRepository
        .findByGroupId(GROUP_ID, Pageable.ofSize(numElements))
        .as(StepVerifier::create)
        .expectNextCount(numElements)
        .verifyComplete();
  }
}
