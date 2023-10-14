package com.clt.usecase;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class CreateGroupUseCaseTest {

    private static final Person OWNER = PersonUtil.newPerson();
    private static final Person MEMBER = PersonUtil.newPerson();
    private static final List<String> MEMBERS_IDS = Arrays.asList(MEMBER.id());
    private static final String GROUP_NAME = "my-friends";
    private GroupStore groupStore;
    private CreateGroupUseCase useCase;

    @BeforeEach
    void initMocks(){
        groupStore = Mockito.mock(GroupStore.class);
        GroupManagerService groupManagerService = new GroupManagerService(new UUIDIdFactory());
        PersonStore personStore = Mockito.mock(PersonStore.class);
        Mockito.when(personStore.retrieve(OWNER.id()))
                .thenReturn(OWNER);
        Mockito.when(personStore.retrieve(MEMBERS_IDS))
                .thenReturn(Collections.singletonList(MEMBER));
        useCase = new CreateGroupUseCase(groupManagerService, personStore, groupStore);
    }

    @Test
    @DisplayName("When creating a group " +
            "Then the group id is not null")
    void create_group_id_test(){
        Group actual = useCase.create(GROUP_NAME, OWNER.id(), MEMBERS_IDS);
        Assertions.assertNotNull(actual.id(), "Missing group id");
    }

    @Test
    @DisplayName("Given a existent owner id " +
            "And given an existent person id as member " +
            "When creating the group " +
            "Then a new group is created with an id " +
            "Then a new group is created with the owner " +
            "And with two members, the owner and the person")
    void create_group_test() {
        Group actual = useCase.create(GROUP_NAME, OWNER.id(), MEMBERS_IDS);
        Assertions.assertEquals(OWNER, actual.owner(), "Wrong group owner");
        Assertions.assertEquals(MEMBERS_IDS.size(), actual.members().size());
        Assertions.assertEquals(MEMBER, actual.members().get(0));
    }
    @Test
    @DisplayName("When creating a group " +
            "Then the created group is stored")
    void created_group_is_stored_test(){
        Group actual = useCase.create(GROUP_NAME, OWNER.id(), MEMBERS_IDS);
        Mockito.verify(groupStore, Mockito.atLeastOnce()).store(actual);
    }

}