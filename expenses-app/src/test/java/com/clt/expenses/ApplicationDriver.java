package com.clt.expenses;

import com.clt.domain.group.User;
import com.clt.domain.view.GroupAggregate;
import com.clt.usecase.CreateGroupWithMembersUseCase;
import com.clt.usecase.FindGroupUseCase;
import com.clt.usecase.FindUserUseCase;
import com.clt.usecase.CreateUserUseCase;
import com.clt.usecase.pojo.NewUser;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is a helper wrapper to interact with the application by masking the complexities of handling randomly generated ids during tests 
 * */
@Component
public class ApplicationDriver {
    private final CreateGroupWithMembersUseCase createGroupUseCase;
    private final FindGroupUseCase findGroupUseCase;
    private final CreateUserUseCase registerPersonUseCase;
    private final FindUserUseCase findUserUseCase;
    private Map<String, String> groupNameToIdMap = new HashMap<>();

    public ApplicationDriver(CreateGroupWithMembersUseCase createGroupUseCase, FindGroupUseCase findGroupUseCase, CreateUserUseCase registerPersonUseCase, FindUserUseCase findUserUseCase) {
        this.createGroupUseCase = createGroupUseCase;
        this.findGroupUseCase = findGroupUseCase;
        this.registerPersonUseCase = registerPersonUseCase;
        this.findUserUseCase = findUserUseCase;
    }

    public GroupAggregate createGroup(String ownerUsername, String groupName, List<String> usernames) {
        List<String> membersId = usernames.stream()
                .map(this::getOrCreateUserId)
                .toList();
        String ownerId = this.getOrCreateUserId(ownerUsername);
        return this.createGroupUseCase.create(groupName, ownerId, membersId).doOnNext( g -> this.groupNameToIdMap.put(g.name(), g.id())).block();
    }

    public String findPerson(String username) {
        return findUserUseCase.retrieve(username).map(User::getId).block();
    }

    /**
     * This method maps the group name to its id to invoke the functionality of find group by id. 
     * It only works if the group has been created by using this abstraction layer
     * @param groupName the name used to create the group
     * @return the group
     **/
    public GroupAggregate retrieveGroup(String groupName) {
        return this.findGroupUseCase.retrieve(this.groupNameToIdMap.get(groupName)).block();
    }

    public String getOrCreateUserId(String username) {
        return findUserUseCase.retrieve(username)
                .onErrorResume((e) -> this.registerPersonUseCase.register(new NewUser(username)))
                .map(User::getId)
                .block();
    }
}
