package com.clt.usecase;

import com.clt.domain.group.*;

import java.util.List;

public class CreateGroupUseCase {
    private final GroupManagerService groupManagerService;
    private final PersonStore personStore;
    private final GroupStore groupStore;

    public CreateGroupUseCase(GroupManagerService groupManagerService, PersonStore personStore, GroupStore groupStore) {
        this.groupManagerService = groupManagerService;
        this.personStore = personStore;
        this.groupStore = groupStore;
    }

    public Group create(String groupName, String ownerId, List<String> memberIds){
        Person owner = personStore.retrieve(ownerId)
                .orElseThrow(PersonNotFound::new);
        List<Person> members = personStore.retrieve(memberIds);
        Group group = groupManagerService.create(groupName, owner, members);
        groupStore.store(group);
        return group;
    }
}
