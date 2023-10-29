package com.clt.expenses.domain.ledger;

import com.clt.domain.expense.Money;
import com.clt.domain.ledger.ExpenseCharge;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ImmutableExpenseCharge;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@SpringBootTest(classes = ExpenseChargeStoreTestConfiguration.class)
class ExpenseChargeStoreImplTest {
  private static final String INSERT_STATEMENT =
      """
                INSERT INTO expense_charge (id, expense, group_id, due_amount, debtor, creditor)
                VALUES(:id, :expense, :group_id, :due_amount, :debtor, :creditor)
                """;

  private static final ExpenseCharge EXPENSE_CHARGE_GROUP_A =
      ImmutableExpenseCharge.builder()
          .id("1")
          .expense("e-id")
          .debtor("d-id")
          .creditor("c-id")
          .groupId("g-id-A")
          .dueAmount(Money.euros(BigDecimal.TEN))
          .build();
  private static final ExpenseCharge EXPENSE_CHARGE_GROUP_B =
      ImmutableExpenseCharge.builder()
          .id("2")
          .expense("e-id")
          .debtor("d-id")
          .creditor("c-id")
          .groupId("g-id-B")
          .dueAmount(Money.euros(BigDecimal.TEN))
          .build();

  @Autowired DatabaseClient databaseClient;
  @Autowired ExpenseChargeRepository expenseChargeRepository;
  @Autowired ExpenseChargeStore store;

  @BeforeEach
  void setup() {
    Hooks.onOperatorDebug();
    var statements =
        Arrays.asList( //
            "DROP TABLE IF EXISTS expense_charge;",
            """
                                CREATE TABLE expense_charge(
                                id varchar primary key,
                                expense varchar(255) not null,
                                group_id varchar,
                                due_amount varchar(66) not null,
                                debtor varchar(255) not null,
                                creditor varchar(255) not null
                                );
                                """);

    statements.forEach(
        it ->
            databaseClient
                .sql(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());
  }

  @DisplayName("Should store a new Expense Charge")
  @Test
  void store_expense_charge_test() {
    store
        .store(EXPENSE_CHARGE_GROUP_A)
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE_GROUP_A)
        .verifyComplete();

    expenseChargeRepository
        .findById(EXPENSE_CHARGE_GROUP_A.id())
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();
  }

  @DisplayName("Should retrieve an existing Expense Charge by id")
  @Test
  void retrieve_existing_expense_by_id_test() {
    databaseClient
        .sql(INSERT_STATEMENT)
        .bind("id", EXPENSE_CHARGE_GROUP_A.id())
        .bind("expense", EXPENSE_CHARGE_GROUP_A.expense())
        .bind("group_id", EXPENSE_CHARGE_GROUP_A.groupId())
        .bind("due_amount", EXPENSE_CHARGE_GROUP_A.dueAmount().getAmount().toString())
        .bind("debtor", EXPENSE_CHARGE_GROUP_A.debtor())
        .bind("creditor", EXPENSE_CHARGE_GROUP_A.creditor())
        .fetch()
        .rowsUpdated()
        .log()
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    store
        .retrieve(EXPENSE_CHARGE_GROUP_A.id())
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE_GROUP_A)
        .verifyComplete();
  }

  @DisplayName("Should retrieve an existing Expense Charge by person and group")
  @Test
  void retrieve_existing_expense_by_person_and_group_test() {
    databaseClient
        .sql(INSERT_STATEMENT)
        .bind("id", EXPENSE_CHARGE_GROUP_A.id())
        .bind("expense", EXPENSE_CHARGE_GROUP_A.expense())
        .bind("group_id", EXPENSE_CHARGE_GROUP_A.groupId())
        .bind("due_amount", EXPENSE_CHARGE_GROUP_A.dueAmount().getAmount().toString())
        .bind("debtor", EXPENSE_CHARGE_GROUP_A.debtor())
        .bind("creditor", EXPENSE_CHARGE_GROUP_A.creditor())
        .fetch()
        .rowsUpdated()
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    databaseClient
        .sql(INSERT_STATEMENT)
        .bind("id", EXPENSE_CHARGE_GROUP_B.id())
        .bind("expense", EXPENSE_CHARGE_GROUP_B.expense())
        .bind("group_id", EXPENSE_CHARGE_GROUP_B.groupId())
        .bind("due_amount", EXPENSE_CHARGE_GROUP_B.dueAmount().getAmount().toString())
        .bind("debtor", EXPENSE_CHARGE_GROUP_B.debtor())
        .bind("creditor", EXPENSE_CHARGE_GROUP_B.creditor())
        .fetch()
        .rowsUpdated()
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    store
        .retrieveBy(EXPENSE_CHARGE_GROUP_A.debtor(), EXPENSE_CHARGE_GROUP_A.groupId())
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE_GROUP_A)
        .verifyComplete();

    store
        .retrieveBy(EXPENSE_CHARGE_GROUP_B.debtor(), EXPENSE_CHARGE_GROUP_B.groupId())
        .as(StepVerifier::create)
        .expectNext(EXPENSE_CHARGE_GROUP_B)
        .verifyComplete();
  }
}
