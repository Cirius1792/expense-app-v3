package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonFactory;
import com.clt.domain.group.PersonStore;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

class RegisterPersonUseCaseTest {


    private static final String USER_NAME = "Mario";
    private static PersonStore store;


    private static RegisterPersonUseCase useCase;

    @BeforeEach
    void initMocks() {
        store = Mockito.mock(PersonStore.class);
        Mockito.when(store.store(Mockito.any()))
                .thenAnswer(args -> args.getArgument(0));
        useCase = new RegisterPersonUseCase(new PersonFactory(new UUIDIdFactory()), store);
    }

    @DisplayName("Given valid data " +
            "When creating a person " +
            "Then the new person is create and stored")
    @Test
    void store_person_test() {
        Person actual = useCase.register(USER_NAME);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(USER_NAME, actual.username(), "Username does not match");
        Mockito.verify(store, Mockito.atLeastOnce()).store(actual);
    }

}