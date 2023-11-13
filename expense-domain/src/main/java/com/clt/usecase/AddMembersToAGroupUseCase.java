package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.*;
import com.clt.domain.view.GroupAggregate;
import reactor.core.publisher.Mono;

import java.util.List;

public class AddMembersToAGroupUseCase implements UseCase {

    private final UserStore personStore;
    private final GroupStore groupStore;
    private final FindGroupUseCase findGroupUseCase;

    public AddMembersToAGroupUseCase(UserStore personStore, GroupStore groupStore, FindGroupUseCase findGroupUseCase) {
        this.personStore = personStore;
        this.groupStore = groupStore;
        this.findGroupUseCase = findGroupUseCase;
    }

    public Mono<GroupAggregate> addMember(String groupId, List<String> userIds) {
        Mono<Group> groupProducer = groupStore.retrieve(groupId)
                .switchIfEmpty(Mono.error(new GroupNotFound(groupId)));
        Mono<List<User>> newMembersProducer = personStore.retrieve(userIds)
                .switchIfEmpty(Mono.error(new PersonNotFound(groupId)))
                .collectList();
        return Mono.zip(groupProducer, newMembersProducer,
                (g, ms) -> groupStore.addMembers(g.getId(), ms.stream().map(User::getId).toList()))
                .flatMap(g -> findGroupUseCase.retrieve(groupId));
    }
}
