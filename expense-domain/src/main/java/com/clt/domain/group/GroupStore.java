package com.clt.domain.group;

import com.clt.domain.commons.Store;
import reactor.core.publisher.Flux;

public interface GroupStore extends Store<Group, String> {
  Flux<Group> retrieveByMember(String userId);
}
