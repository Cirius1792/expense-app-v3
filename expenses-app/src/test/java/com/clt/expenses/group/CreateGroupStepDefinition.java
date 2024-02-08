package com.clt.expenses.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.clt.usecase.AddMembersToAGroupUseCase;
import com.clt.usecase.CreateGroupWithMembersUseCase;

import io.cucumber.java.en.When;
import reactor.test.StepVerifier;

public class CreateGroupStepDefinition {



    @Autowired
    private CreateGroupWithMembersUseCase createGroupWithMembersUseCase;
    @Autowired
    private AddMembersToAGroupUseCase addMembersToAGroupUseCase;

	@When("{string} creates the group {string}")
	public void creates_the_group(String userName, String groupName) {
		createGroupWithMembersUseCase.create(groupName, userName, new ArrayList<>())
			.as(StepVerifier::create)
			.expectNextCount(1)
			.verifyComplete();
	}

	@When("{string} adds {string} to the group {string}")
	public void adds_to_the_group(String groupOwner, String newMember, String groupName) {
		addMembersToAGroupUseCase
			.addMembers(groupOwner, List.of(newMember))
			.as(StepVerifier::create)
			.expectNextCount(1)
			.verifyComplete();
	}
}
