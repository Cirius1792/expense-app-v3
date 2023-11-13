package com.clt.expenses.domain.group;

import com.clt.domain.group.ImmutableUser;
import com.clt.domain.group.User;
import com.clt.domain.group.UserStore;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@SpringBootTest(classes = GroupTestConfiguration.class)
class UserStoreImplTest {
  private static final String ID = "id";
  private static final String USERNAME = "username";
  @Autowired DatabaseClient database;
  @Autowired
  UserStore personStore;
  @Autowired
  UserRepository personRepository;

  @BeforeEach
  void setUp() {

    Hooks.onOperatorDebug();
    var statements =
        Arrays.asList( //
            "DROP TABLE IF EXISTS person;",
            """
                                CREATE TABLE person (
                                id VARCHAR PRIMARY KEY,
                                username VARCHAR(255)
                                );
                                """);

    statements.forEach(
        it ->
            database
                .sql(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());
  }

  private User buildPerson() {
    return ImmutableUser.builder().id(ID).username(USERNAME).build();
  }

  private UserEntity buildPersonEntity() {
    UserEntity entity = new UserEntity();
    entity.setId(ID);
    entity.setUsername(USERNAME);
    return entity;
  }

  @DisplayName("Should store a user")
  @Test
  void store_person_test() {
    User expected = buildPerson();
    personStore.store(expected).as(StepVerifier::create).expectNext(expected).verifyComplete();
    personRepository
        .findById(ID)
        .as(StepVerifier::create)
        .expectNext(buildPersonEntity())
        .verifyComplete();
  }

  @DisplayName("Should retrieve a stored user")
  @Test
  void retrieve_person_test() {
    personRepository
        .save(buildPersonEntity())
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();
    personStore.retrieve(ID).as(StepVerifier::create).expectNext(buildPerson()).verifyComplete();
  }
}
