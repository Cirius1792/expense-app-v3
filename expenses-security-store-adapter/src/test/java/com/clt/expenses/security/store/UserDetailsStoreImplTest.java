package com.clt.expenses.security.store;

import com.clt.expenses.security.ApplicationUser;
import com.clt.expenses.security.UserDetailsStore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;

import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;

@SpringBootTest(classes = UserDetailsStoreTestConfiguration.class)
class UserDetailsStoreImplTest {

    public static final String INSERT_STATEMENT =
            "INSERT INTO application_user VALUES (:id, :password)";
    private static final ApplicationUser USER = new ApplicationUser("id", "password");
    @Autowired DatabaseClient databaseClient;
    @Autowired UserDetailsStore userDetailsStore;
    @Autowired ApplicationUserRepository applicationUserRepository;

    @BeforeEach
    void setup() {
        Hooks.onOperatorDebug();
        var statements =
                Arrays.asList( //
                        "DROP TABLE IF EXISTS application_user;",
                        """
                                            CREATE TABLE application_user(
                                            id varchar primary key,
                                            password varchar(255) not null
                                            );
                                            """);

        statements.forEach(
                it ->
                        databaseClient
                                .sql(it) //
                                .fetch() //
                                .rowsUpdated() //
                                .as(StepVerifier::create) //
                                .expectNextCount(1) //
                                .verifyComplete());
    }

    @DisplayName("Should retrieve an existing user")
    @Test
    void retrieve_existing_expense_by_id_test() {
        databaseClient
                .sql(INSERT_STATEMENT)
                .bind("id", USER.id())
                .bind("password", USER.password())
                .fetch()
                .rowsUpdated()
                .log()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        userDetailsStore
                .findByUsername(USER.id())
                .as(StepVerifier::create)
                .expectNext(USER)
                .verifyComplete();
    }

    @DisplayName("Should store a new user")
    @Test
    void store_new_user_test() {
        userDetailsStore.store(USER).as(StepVerifier::create).verifyComplete();
        applicationUserRepository
                .findById(USER.id())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
