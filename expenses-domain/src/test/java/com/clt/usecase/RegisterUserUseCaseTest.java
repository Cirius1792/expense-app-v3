package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;

import com.clt.usecase.pojo.NewUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class RegisterUserUseCaseTest {

    private static final String USER_NAME = "Mario";
    private static final String PASSWORD = "qwerty";
    private static UserStore store;

    private static RegisterUserUseCase useCase;

    @BeforeEach
    void initMocks() {
        store = Mockito.mock(UserStore.class);
        Mockito.when(store.store(Mockito.any())).thenAnswer(args -> Mono.just(args.getArgument(0)));
        useCase = new RegisterUserUseCase(new UserFactory(new UUIDIdFactory()), store);
    }

    @DisplayName(
            """
    Given a valid username and password
                    When creating a user
                    Then the new user is created and stored
                    """)
    @Test
    void store_person_test() {
        NewUser newUser = new NewUser(USER_NAME, PASSWORD);
        var producer = useCase.register(newUser);
        StepVerifier.create(producer)
                .assertNext(
                        actual -> {
                            Assertions.assertNotNull(actual);
                            Assertions.assertEquals(
                                    USER_NAME, actual.getId(), "Username does not match");
                            Mockito.verify(store, Mockito.atLeastOnce()).store(actual);
                        })
                .verifyComplete();
    }
}
