package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.ImmutableUser;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;

import com.clt.domain.registry.InvalidUsernameError;
import com.clt.event.GenericEvent;
import com.clt.event.Observer;
import com.clt.usecase.pojo.NewUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

class RegisterUserUseCaseTest {

    private static final String USER_NAME = "Mario";

    private static final String USER_NAME_ALREADY_PRESENT = "Fabio";

    private static final NewUser NEW_USER = new NewUser(USER_NAME);
    private static UserStore store;
    private static CreateUserUseCase useCase;
    private static Observer<NewUser> newUserNotifier;

    @BeforeEach
    void initMocks() {
        store = Mockito.mock(UserStore.class);
        Mockito.when(store.store(Mockito.any())).thenAnswer(args -> Mono.just(args.getArgument(0)));
        Mockito.when(store.retrieve(USER_NAME)).thenReturn(Mono.empty());
        Mockito.when(store.retrieve(USER_NAME_ALREADY_PRESENT))
                .thenReturn(
                        Mono.just(ImmutableUser.builder().id(USER_NAME_ALREADY_PRESENT).build()));
        newUserNotifier = Mockito.mock(Observer.class);
        Mockito.when(newUserNotifier.notify(NEW_USER))
                .thenReturn(Mono.just(new GenericEvent<>("1", NEW_USER)));
        useCase =
                new CreateUserUseCase(
                        new UserFactory(new UUIDIdFactory()), store, newUserNotifier);
    }

    @DisplayName(
            """
                    Given a valid username
                    When creating a user
                    Then the new user is created and stored
                    """)
    @Test
    void store_person_test() {
        var producer = useCase.register(NEW_USER);
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

    @DisplayName(
            """
                Given a registered user
                When another user tries to register with the same username
                Then an InvalidUserNameError is returned
               """)
    @Test
    void should_fail_because_of_unavailable_username() {
        NewUser newUser = new NewUser(USER_NAME_ALREADY_PRESENT);
        var producer = useCase.register(newUser);
        StepVerifier.create(producer).expectError(InvalidUsernameError.class).verify();
    }

    @DisplayName(
            """
                Given a registered notifier
                When a new user is registered
                Then a NewUser Event is propagated
                """)
    @Test
    void should_notify_new_user() {
        useCase.register(NEW_USER)
                .as(StepVerifier::create)
                .assertNext(
                        u ->
                                Mockito.verify(newUserNotifier, Mockito.atLeastOnce())
                                        .notify(NEW_USER))
                .verifyComplete();
        ;
    }
}
