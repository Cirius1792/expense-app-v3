package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.*;
import com.clt.domain.view.GroupAggregate;
import reactor.core.publisher.Mono;

import java.util.List;

public class AddMembersToAGroupUseCase implements UseCase {

    private final PersonStore personStore;
    private final GroupStore groupStore;
    private final FindGroupUseCase findGroupUseCase;

    public AddMembersToAGroupUseCase(PersonStore personStore, GroupStore groupStore, FindGroupUseCase findGroupUseCase) {
        this.personStore = personStore;
        this.groupStore = groupStore;
        this.findGroupUseCase = findGroupUseCase;
    }

    public Mono<GroupAggregate> addMember(String groupId, List<String> userIds) {
        Mono<Group> groupProducer = groupStore.retrieve(groupId)
                .switchIfEmpty(Mono.error(new GroupNotFound(groupId)));
        Mono<List<Person>> newMembersProducer = personStore.retrieve(userIds)
                .switchIfEmpty(Mono.error(new PersonNotFound(groupId)))
                .collectList();
        return Mono.zip(groupProducer, newMembersProducer,
                (g, ms) -> groupStore.addMembers(g.id(), ms.stream().map(Person::id).toList()))
                .flatMap(g -> findGroupUseCase.retrieve(groupId));
    }
}
