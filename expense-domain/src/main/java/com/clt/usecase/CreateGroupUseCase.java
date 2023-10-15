package com.clt.usecase;

import com.clt.domain.group.*;
import reactor.core.publisher.Mono;

import java.util.List;

public class CreateGroupUseCase {
    private final GroupFactory groupFactory;
    private final PersonStore personStore;
    private final GroupStore groupStore;

    public CreateGroupUseCase(GroupFactory groupFactory, PersonStore personStore, GroupStore groupStore) {
        this.groupFactory = groupFactory;
        this.personStore = personStore;
        this.groupStore = groupStore;
    }

    public Mono<Group> create(String groupName, String ownerId, List<String> memberIds){
        Person owner = personStore.retrieve(ownerId)
                .orElseThrow(PersonNotFound::new);
        List<Person> members = personStore.retrieve(memberIds);
        Group group = groupFactory.create(groupName, owner, members);
        return groupStore.store(group);
    }
}
