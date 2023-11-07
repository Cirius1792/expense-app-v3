package com.clt.scenario;

import io.cucumber.java.en.When;
import reactor.core.publisher.Mono;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonFactory;
import com.clt.domain.group.PersonStore;
import com.clt.usecase.RegisterPersonUseCase;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HandleUserScenario {
	private String username;
	private Person actualPerson;

	private PersonStore store;

	private RegisterPersonUseCase useCase;

	@Before
	public void initMocks() {
		store = Mockito.mock(PersonStore.class);
		Mockito.when(store.store(Mockito.any())).thenAnswer(args -> Mono.just(args.getArgument(0)));
		this.useCase = new RegisterPersonUseCase(new PersonFactory(new UUIDIdFactory()), store);
	}

	@Given("a new user with username {string}")
	public void a_new_user_with_username(String username) {
		this.username = username;
	}

	@When("creating the new user")
	public void creating_the_new_user() {
		this.actualPerson = useCase.register(this.username).block();
	}

	@Then("the new user is created with the username {string}")
	public void the_new_user_is_created_with_the_username(String username) {
		Assertions.assertEquals(username, actualPerson.username());
	}

	@Then("the new user is stored")
	public void the_new_user_is_stored() {
		Mockito.verify(store, Mockito.atLeastOnce()).store(actualPerson);
	}
}
