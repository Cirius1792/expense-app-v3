package com.clt.usecase;

import com.clt.domain.group.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FindGroupUseCase {

    private final GroupStore groupStore;
    private final PersonStore personStore;

    public FindGroupUseCase(GroupStore groupStore, PersonStore personStore) {
        this.groupStore = groupStore;
        this.personStore = personStore;
    }

    public Mono<GroupAggregate> retrieve(String groupId) {
        return groupStore.retrieve(groupId)
                .switchIfEmpty(Mono.error(new GroupNotFound()))
                .flatMap(g -> personStore.retrieve(allMembers(g))
                                .collectList()
                                .transform(membersProducer -> buildAggregate(g, membersProducer))

                );
    }

    private Publisher<GroupAggregate> buildAggregate(Group g, Mono<List<Person>> membersProducer) {
        return membersProducer.map(members -> ImmutableGroupAggregate.builder()
                        .id(g.id())
                        .name(g.name())
                        .owner(members.stream().filter(m -> m.id().equals(g.owner())).findFirst().orElseThrow())
                        .members(members)
                        .build());
    }

    private List<String> allMembers(Group g) {
        List<String> membersAndOwner = new ArrayList<>(g.members());
        membersAndOwner.add(g.owner());
        return membersAndOwner;
    }
}
