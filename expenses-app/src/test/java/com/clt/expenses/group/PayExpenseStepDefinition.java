package com.clt.expenses.group;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.Money;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.GroupAggregate;
import com.clt.expenses.ApplicationDriver;
import com.clt.usecase.AddExpenseUseCase;
import com.clt.usecase.PayUseCase;
import com.clt.usecase.RetrieveUserBalancePerGroupUseCase;
import com.clt.usecase.RetrieveUserDebtUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class PayExpenseStepDefinition {
  private static final Logger logger = LoggerFactory.getLogger(PayExpenseStepDefinition.class);

  @Autowired
  private ApplicationDriver applicationDriver;
  @Autowired private AddExpenseUseCase addExpenseUseCase;
  @Autowired private PayUseCase payExpenseUseCase;
  @Autowired private RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase;
  @Autowired private RetrieveUserDebtUseCase retrieveUserDebtUseCase;


  private List<ExpenseAggregate> expenses;
  private GroupAggregate group;
  private Money balance;

  @Given("the group {string} having the expenses")
  public void theGroupHavingTheExpenses(String groupName, List<Expense> newExpenses) {
    logger.info("Expense: {}", newExpenses);
    this.expenses = new ArrayList<>();
    group = this.applicationDriver.retrieveGroup(groupName);
    newExpenses.forEach(
        e ->
            this.addExpenseUseCase
                .create(e.getDescription(), e.getAmount(), e.getOwner(), group.id())
                .doOnNext(expenses::add)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete());
  }

    @Given("{string} retrieves his debt to {string}")
    public void retrieves_his_debt_to_alice(String user, String creditor) {
      this.balance = this.retrieveUserDebtUseCase.retrieveFor(this.group.id(), user, creditor)
              .block();
   }
    @When("{string} pays its debt to {string}")
    public void pays_its_debt_to(String payer, String payed) {
    this.payExpenseUseCase
        .pay(this.group.id(), payer, payed, this.balance)
        .as(StepVerifier::create)
        .assertNext(paymentCharge -> Assertions.assertEquals(paymentCharge.getAmount(), this.balance))
        .verifyComplete();
    }
  @Then("The debt is {string}")
  public void theDebtIs(String balance) {
    Assertions.assertEquals(this.balance, Money.euros(balance));
 }

}
