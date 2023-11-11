package com.clt.domain.group;

import com.clt.domain.commons.Store;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GroupStore extends Store<Group, String> {
  Flux<Group> retrieveByMember(String userId);
  Mono<Group> addMembers(String groupId, List<String> members);
}
