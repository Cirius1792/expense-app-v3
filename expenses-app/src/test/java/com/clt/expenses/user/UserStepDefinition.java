package com.clt.expenses.user;

import com.clt.domain.group.User;
import com.clt.usecase.RegisterPersonUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserStepDefinition {

    @Autowired
    private RegisterPersonUseCase registerPersonUseCase;
    private String username;
    private User newUser;

    @Given("a new user with username {string}")
    public void a_new_user_with_username(String username) {
        this.username = username;
    }

    @When("the user submits a register request")
    public void the_user_submits_a_register_request() {
        newUser = registerPersonUseCase.register(this.username)
                .block();
    }

    @Then("the user unique identifier is returned")
    public void the_user_unique_identifier_is_returned() {
        Assertions.assertTrue(StringUtils.isNotBlank(newUser.getId()), "Missing user identifier");
    }

    @Then("the username is {string}")
    public void the_username_is(String username) {
        Assertions.assertEquals(this.newUser.getUsername(), username, "Username does not match");
    }
}
