package com.clt.expenses.group;

import com.clt.domain.group.User;
import com.clt.domain.view.GroupAggregate;
import com.clt.expenses.ApplicationDriver;
import com.clt.usecase.AddMembersToAGroupUseCase;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
import com.clt.usecase.RegisterUserUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class GroupStepDefinition {

  @Autowired private RegisterUserUseCase registerPersonUseCase;
  @Autowired private CreateGroupUseCase createGroupUseCase;
  @Autowired private FindGroupUseCase findGroupUseCase;
  @Autowired private AddMembersToAGroupUseCase addMembersToAGroupUseCase;
  @Autowired private ApplicationDriver applicationDriver;

  @Autowired private TestUsersInformation users;
  @Autowired private TestGroupInformation newGroup;

  private GroupAggregate retrievedGroup;

  @Given("a user {string}")
  public void a_user(String username) {
    this.users.put(username, this.applicationDriver.getOrCreateUserId(username));
  }

  @When("{string} creates the group {string} with the members:")
  public void creates_the_group_with_the_members(
      String ownerUsername, String groupName, List<String> usernames) {
    this.newGroup.set(this.applicationDriver.createGroup(ownerUsername, groupName, usernames));
  }

  @Then("{string} is the owner of the group")
  public void is_the_owner_of_the_group(String ownerUsername) {
    Assertions.assertEquals(
        this.users.get(ownerUsername),
        this.newGroup.get().owner().getId(),
        "Owner id does not match");
  }

  @Then("the members of the group {string} are:")
  public void the_members_of_the_group_are(String group, List<String> usernames) {
    Set<String> memebrIds = new HashSet<>(usernames);
    Assertions.assertTrue(
        memebrIds.containsAll(this.applicationDriver.retrieveGroup(group).members().stream().map(User::getId).toList()),
        "Expected members does not match");
  }

  @Then("the group id is not null")
  public void the_group_id_is_not_null() {
    Assertions.assertNotNull(this.newGroup.get().id());
  }

  @Given("a group {string} with the owner {string} and members:")
  public void a_group_with_the_owner_and_members(
      String groupName, String ownerUsername, List<String> usernames) {
    this.newGroup.set(this.applicationDriver.createGroup(ownerUsername, groupName, usernames));
  }

  @When("looking for the group {string} by id")
  public void looking_for_the_group_by_id(String groupName) {
    this.retrievedGroup = this.findGroupUseCase.retrieve(this.newGroup.get().id()).block();
  }

  @Then("the group {string} is returned")
  public void the_group_is_returned(String groupName) {
    Assertions.assertEquals(this.newGroup.get(), this.retrievedGroup);
  }

  @When("{string} is added to the group")
  public void isAddedToTheGroup(String username) {
    this.newGroup.set(
        addMembersToAGroupUseCase
            .addMember(this.newGroup.get().id(), List.of(this.users.get(username)))
            .block());
  }
}
