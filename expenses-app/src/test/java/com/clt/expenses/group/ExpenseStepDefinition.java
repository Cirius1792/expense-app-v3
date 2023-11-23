package com.clt.expenses.group;

import com.clt.domain.expense.Expense;
import com.clt.domain.view.ExpenseAggregate;
import com.clt.domain.view.GroupAggregate;
import com.clt.expenses.ApplicationDriver;
import com.clt.usecase.AddExpenseUseCase;
import com.clt.usecase.FindExpenseUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseStepDefinition {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseStepDefinition.class);

    @Autowired
    private ApplicationDriver applicationDriver;

    @Autowired
    private AddExpenseUseCase addExpenseUseCase;
    @Autowired
    private FindExpenseUseCase findExpenseUseCase;
    private List<ExpenseAggregate> expenseList;
    private List<Expense> expectedExpenses;
    private ExpenseAggregate actualExpense;
    private Map<String, String> expenseIdMap = new HashMap<>();

    @Given("the group {string}")
    public void theGroup(String groupName) {
        Assertions.assertNotNull(applicationDriver.retrieveGroup(groupName));
    }

    @When("{string} adds the expenses:")
    public void addsTheExpenses(String expenseOwner, List<Expense> expenseList) {
        this.expectedExpenses = new ArrayList<>(expenseList);
        this.expenseList = expenseList.stream()
                .map(e -> this.addExpenseUseCase.create(
                        e.getDescription(),
                        e.getAmount(),
                        this.applicationDriver.findPerson(expenseOwner),
                        this.applicationDriver.retrieveGroup(e.getGroupId()).id()
                ).block()).toList();
    }

    @Then("the new expense is created")
    public void theNewExpenseIsCreated() {
        Assertions.assertEquals(expectedExpenses.size(), expenseList.size());
    }

    @And("the new expense has a unique id")
    public void theNewExpenseHasAUniqueId() {
        this.expenseList.forEach(e -> Assertions.assertNotNull(e.getId()));
    }

    @Given("the expense:")
    public void theExpense(Expense expense) {
        ExpenseAggregate e = this.addExpenseUseCase.create(
                expense.getDescription(),
                expense.getAmount(),
                this.applicationDriver.findPerson(expense.getOwner()),
                this.applicationDriver.retrieveGroup(expense.getGroupId()).id()
        ).block();
        Assertions.assertNotNull(e);
        this.expenseIdMap.put(expense.getId(), e.getId());
    }

    @When("looking for the expense {string}")
    public void lookingForTheExpense(String expenseId) {
        this.actualExpense = this.findExpenseUseCase.retrieveExpense(this.expenseIdMap.get(expenseId)).block();
    }

    @Then("the expense is returned:")
    public void theExpenseIsReturned(Expense expense) {
        Assertions.assertEquals(expense.getDescription(), this.actualExpense.getDescription());
        Assertions.assertEquals(this.applicationDriver.findPerson(expense.getOwner()), this.actualExpense.getOwner().getId());
        Assertions.assertEquals(expense.getAmount(), this.actualExpense.getAmount());
    }
}
