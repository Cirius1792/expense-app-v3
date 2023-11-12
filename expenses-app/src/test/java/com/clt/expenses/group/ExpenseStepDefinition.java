package com.clt.expenses.group;

import com.clt.domain.expense.Expense;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.GroupAggregate;
import com.clt.expenses.ApplicationDriver;
import com.clt.usecase.AddExpenseUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExpenseStepDefinition {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseStepDefinition.class);

    @Autowired
    private ApplicationDriver applicationDriver;

    @Autowired
    private AddExpenseUseCase addExpenseUseCase;
    private GroupAggregate group;
    private List<ExpenseAggregate> expenseList;
    private List<Expense> expectedExpenses;

    @Given("the group {string}")
    public void theGroup(String groupName) {
        this.group = applicationDriver.retrieveGroup(groupName);
    }

    @When("{string} adds the expenses:")
    public void addsTheExpenses(String expenseOwner, List<Expense> expenseList) {
        this.expectedExpenses = expenseList;
        this.expenseList = expenseList.stream()
                .map(e -> this.addExpenseUseCase.create(
                        e.description(),
                        e.amount(),
                        this.applicationDriver.findPersonIdByUsername(expenseOwner),
                        this.group.id()
                ).block()).toList();
    }

    @Then("the new expense is created")
    public void theNewExpenseIsCreated() {
        Assertions.assertEquals(expectedExpenses.size(), expenseList.size());
    }

    @And("the new expense has a unique id")
    public void theNewExpenseHasAUniqueId() {
        this.expenseList.forEach(e -> Assertions.assertNotNull(e.id()));
    }
}
