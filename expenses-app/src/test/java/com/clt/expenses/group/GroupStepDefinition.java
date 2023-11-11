package com.clt.expenses.group;

import com.clt.domain.group.Group;
import com.clt.domain.group.Person;
import com.clt.domain.view.GroupAggregate;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.RegisterPersonUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.*;
import java.util.stream.Collectors;

public class GroupStepDefinition {

    @Autowired
    private RegisterPersonUseCase registerPersonUseCase;
    @Autowired
    private CreateGroupUseCase createGroupUseCase;
    private Map<String, Person> users = new HashMap<>();
    private GroupAggregate group;
    private String ownerUsername;

    @Given("a user {string}")
    public void a_user(String username) {
        Person person =  this.registerPersonUseCase.register(username).block();
        Assertions.assertNotNull(person);
        this.users.put(person.username(), person);
    }

    @When("{string} creates the group {string} with the members:")
    public void creates_the_group_with_the_members(String ownerUsername, String groupName, List<String> usernames) {
        Person owner = this.users.get(ownerUsername);
        Assertions.assertNotNull(owner, "The owner does not exists");
        List<String> members = usernames.stream()
                .map(un -> this.users.get(un))
                .map(Person::id)
                .toList();
        Assertions.assertEquals(members.size(), usernames.size(), "Users not founds");
        this.group = this.createGroupUseCase.create(groupName, owner.id(), members)
                .block();

    }

    @Then("{string} is the owner of the group")
    public void is_the_owner_of_the_group(String ownerUsername) {
        Assertions.assertEquals(this.users.get(ownerUsername).id(), this.group.owner().id(), "Owner id does not match");
    }

    @Then("the members of the group are:")
    public void the_members_of_the_group_are(List<String> usernames) {
        Set<String> memebrIds = this.users.entrySet()
                .stream().filter(e -> usernames.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .map(Person::id)
                .collect(Collectors.toSet());
        Assertions.assertTrue(memebrIds.containsAll(this.group.members()
                .stream().map(Person::id)
                .toList()), "Expected members does not match");
    }

    @Then("the group id is not null")
    public void the_group_id_is_not_null() {
        Assertions.assertNotNull(this.group.id());
    }

}
