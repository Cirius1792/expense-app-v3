package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CreateGroupUseCaseTest {

    private static final String INVALID_PERSON_ID = "ipid";
    private static final User OWNER = PersonUtil.newPerson();
    private static final User MEMBER = PersonUtil.newPerson();
    private static final List<String> MEMBERS_IDS = Arrays.asList(MEMBER.getId());
    private static final String GROUP_NAME = "my-friends";
    private GroupStore groupStore;
    private CreateGroupUseCase useCase;

    @BeforeEach
    void initMocks() {
        groupStore = Mockito.mock(GroupStore.class);
        GroupFactory groupFactory = new GroupFactory(new UUIDIdFactory());
        UserStore personStore = Mockito.mock(UserStore.class);
        Mockito.when(personStore.retrieve(INVALID_PERSON_ID)).thenReturn(Mono.empty());
        Mockito.when(groupStore.store(Mockito.any()))
                .thenAnswer(args -> Mono.just(args.getArgument(0)));
        Mockito.when(personStore.retrieve(OWNER.getId())).thenReturn(Mono.just(OWNER));
        Mockito.when(personStore.retrieve(MEMBERS_IDS)).thenReturn(Flux.just(MEMBER));
        useCase = new CreateGroupUseCase(groupFactory, personStore, groupStore);
    }

    @Test
    @DisplayName("""
            When creating a group 
            Then the group id is not null
            """)
    void create_group_id_test() {
        var producer = useCase.create(GROUP_NAME, OWNER.getId(), MEMBERS_IDS);
        StepVerifier.create(producer)
                .assertNext(actual -> Assertions.assertNotNull(actual.id(), "Missing group id"))
                .verifyComplete();
    }

    @Test
    @DisplayName(
            "Given a existent owner id "
                    + "And given an existent person id as member "
                    + "When creating the group "
                    + "Then a new group is created with an id "
                    + "Then a new group is created with the owner "
                    + "And with two members, the owner and the person")
    void create_group_test() {
        var producer = useCase.create(GROUP_NAME, OWNER.getId(), MEMBERS_IDS);
        StepVerifier.create(producer)
                .assertNext(
                        actual -> {
                            Assertions.assertEquals(OWNER, actual.owner(), "Wrong group owner");
                            Assertions.assertEquals(MEMBERS_IDS.size() + 1, actual.members().size());
                            Assertions.assertEquals(MEMBER, actual.members().toArray()[0]);
                        })
                .verifyComplete();
    }

    @Test
    @DisplayName("When creating a group " + "Then the created group is stored")
    void created_group_is_stored_test() {
        var producer = useCase.create(GROUP_NAME, OWNER.getId(), MEMBERS_IDS);
        StepVerifier.create(producer)
                .assertNext(
                        actual -> Mockito.verify(groupStore, Mockito.atLeastOnce()).store(Mockito.any()))
                .verifyComplete();
    }

    @Test
    @DisplayName(
            "Given a wrong owner id "
                    + "When creating the group "
                    + "Then a PersonNotFound error is thrown")
    void wrong_owner_id_test() {
        var producer = useCase.create(GROUP_NAME, INVALID_PERSON_ID, MEMBERS_IDS);
        StepVerifier.create(producer).verifyError(PersonNotFound.class);
    }
}
