package com.clt.domain.group;

import reactor.core.publisher.Mono;

public interface GroupStore {
    Mono<Group> store(Group group);
    Mono<Group> retrieve(String groupId);
}
