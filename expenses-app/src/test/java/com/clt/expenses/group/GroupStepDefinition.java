package com.clt.expenses.group;

import com.clt.domain.group.User;
import com.clt.domain.view.GroupAggregate;
import com.clt.expenses.ApplicationDriver;
import com.clt.usecase.AddMembersToAGroupUseCase;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
import com.clt.usecase.CreateUserUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class GroupStepDefinition {

  @Autowired private FindGroupUseCase findGroupUseCase;
  @Autowired private AddMembersToAGroupUseCase addMembersToAGroupUseCase;
  @Autowired private ApplicationDriver applicationDriver;

  @Autowired private TestGroupInformation newGroup;

  private GroupAggregate retrievedGroup;

  @Given("a user {string}")
  public void a_user(String userId) {
    this.applicationDriver.getOrCreateUserId(userId);
  }

  @When("{string} creates the group {string} with the members:")
  public void creates_the_group_with_the_members(
      String ownerUserId, String groupName, List<String> userIds) {
    this.newGroup.set(this.applicationDriver.createGroup(ownerUserId, groupName, userIds));
  }

  @Then("{string} is the owner of the group")
  public void is_the_owner_of_the_group(String ownerUserId) {
    Assertions.assertEquals(
        ownerUserId,
        this.newGroup.get().owner().getId(),
        "Owner id does not match");
  }

  @Then("the members of the group {string} are:")
  public void the_members_of_the_group_are(String group, List<String> usernames) {
    Set<String> memebrIds = new HashSet<>(usernames);
    Assertions.assertTrue(
        memebrIds.containsAll(
            this.applicationDriver.retrieveGroup(group).members().stream()
                .map(User::getId)
                .toList()),
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
  public void isAddedToTheGroup(String userId) {
    this.newGroup.set(
        addMembersToAGroupUseCase
            .addMember(this.newGroup.get().id(), List.of(this.applicationDriver.findPerson(userId)))
            .block());
  }
}
