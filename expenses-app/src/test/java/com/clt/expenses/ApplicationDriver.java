package com.clt.expenses;

import com.clt.domain.group.User;
import com.clt.domain.view.GroupAggregate;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
import com.clt.usecase.FindUserUseCase;
import com.clt.usecase.RegisterPersonUseCase;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationDriver {
    private final CreateGroupUseCase createGroupUseCase;
    private final FindGroupUseCase findGroupUseCase;
    private final RegisterPersonUseCase registerPersonUseCase;
    private final FindUserUseCase findUserUseCase;
    private Map<String, String> groupNameToIdMap = new HashMap<>();
    private Map<String, String> usernameToIdMap = new HashMap<>();

    public ApplicationDriver(CreateGroupUseCase createGroupUseCase, FindGroupUseCase findGroupUseCase, RegisterPersonUseCase registerPersonUseCase, FindUserUseCase findUserUseCase) {
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

    public String findPersonIdByUsername(String username) {
        return this.usernameToIdMap.get(username);
    }

    public GroupAggregate retrieveGroup(String groupName) {
        return this.findGroupUseCase.retrieve(this.groupNameToIdMap.get(groupName)).block();
    }

    public String getOrCreateUserId(String username) {
        String id;
        if ((id = this.usernameToIdMap.get(username)) != null)
            return id;
        id = this.registerPersonUseCase.register(username).map(User::getId).block();
        this.usernameToIdMap.putIfAbsent(username, id);
        return id;
    }
}
