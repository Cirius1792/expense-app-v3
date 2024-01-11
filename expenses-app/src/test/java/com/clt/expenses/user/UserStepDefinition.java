package com.clt.expenses.user;

import com.clt.usecase.RegisterUserUseCase;
import com.clt.usecase.pojo.NewUser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserStepDefinition {

    @Autowired
    private RegisterUserUseCase registerPersonUseCase;
    @Autowired
    private TestUser testUser;

    @Given("the user types the password {string}")
    public void the_user_types_the_password(String password){
        this.testUser.setPassword(password);
    }
    @When("the user submits a register request")
    public void the_user_submits_a_register_request() {
        registerPersonUseCase.register(new NewUser(this.testUser.getUserId(), this.testUser.getPassword()))
                .doOnSuccess(this.testUser::set)
                .block();
    }

    @Then("the user unique identifier is returned")
    public void the_user_unique_identifier_is_returned() {
        Assertions.assertTrue(StringUtils.isNotBlank(testUser.getUserId()), "Missing user identifier");
    }

    @Then("the username is {string}")
    public void the_username_is(String username) {
        Assertions.assertEquals(this.testUser.getUserId(), username, "Username does not match");
    }

    @And("the user types the id {string}")
    public void theUserTypeTheId(String userId) {
        this.testUser.setUserId(userId);
    }

    @Then("the user unique identifier is {string}")
    public void theUserUniqueIdentifierIs(String expectedUserId) {
        Assertions.assertEquals(expectedUserId, this.testUser.getUserId());
    }
}
