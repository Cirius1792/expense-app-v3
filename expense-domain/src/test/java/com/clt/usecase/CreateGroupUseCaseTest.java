package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

class CreateGroupUseCaseTest {

    private static final String INVALID_PERSON_ID = "ipid";
    private static final Person OWNER = PersonUtil.newPerson();
    private static final Person MEMBER = PersonUtil.newPerson();
    private static final List<String> MEMBERS_IDS = Arrays.asList(MEMBER.id());
    private static final String GROUP_NAME = "my-friends";
    private GroupStore groupStore;
    private CreateGroupUseCase useCase;

    @BeforeEach
    void initMocks() {
        groupStore = Mockito.mock(GroupStore.class);
        GroupFactory groupFactory = new GroupFactory(new UUIDIdFactory());
        PersonStore personStore = Mockito.mock(PersonStore.class);
        Mockito.when(personStore.retrieve(INVALID_PERSON_ID))
                .thenReturn(Mono.empty());
        Mockito.when(groupStore.store(Mockito.any()))
                .thenAnswer(args -> Mono.just(args.getArgument(0)));
        Mockito.when(personStore.retrieve(OWNER.id()))
                .thenReturn(Mono.just(OWNER));
        Mockito.when(personStore.retrieve(MEMBERS_IDS))
                .thenReturn(Flux.just(MEMBER));
        useCase = new CreateGroupUseCase(groupFactory, personStore, groupStore);
    }

    @Test
    @DisplayName("When creating a group " +
            "Then the group id is not null")
    void create_group_id_test() {
        var producer = useCase.create(GROUP_NAME, OWNER.id(), MEMBERS_IDS);
        StepVerifier.create(producer)
                .assertNext(actual -> Assertions.assertNotNull(actual.id(), "Missing group id"))
                .verifyComplete();

    }

    @Test
    @DisplayName("Given a existent owner id " +
            "And given an existent person id as member " +
            "When creating the group " +
            "Then a new group is created with an id " +
            "Then a new group is created with the owner " +
            "And with two members, the owner and the person")
    void create_group_test() {
        var producer = useCase.create(GROUP_NAME, OWNER.id(), MEMBERS_IDS);
        StepVerifier.create(producer)
                .assertNext(actual -> {
                    Assertions.assertEquals(OWNER, actual.owner(), "Wrong group owner");
                    Assertions.assertEquals(MEMBERS_IDS.size(), actual.members().size());
                    Assertions.assertEquals(MEMBER, actual.members().get(0));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("When creating a group " +
            "Then the created group is stored")
    void created_group_is_stored_test() {
        var producer = useCase.create(GROUP_NAME, OWNER.id(), MEMBERS_IDS);
        StepVerifier.create(producer)
                .assertNext(actual -> Mockito.verify(groupStore, Mockito.atLeastOnce()).store(actual))
                .verifyComplete();
    }

    @Test
    @DisplayName("Given a wrong owner id " +
            "When creating the group " +
            "Then a PersonNotFound error is thrown")
    void wrong_owner_id_test() {
        var producer = useCase.create(GROUP_NAME, INVALID_PERSON_ID, MEMBERS_IDS);
        StepVerifier.create(producer)
                        .verifyError(PersonNotFound.class);
    }

}