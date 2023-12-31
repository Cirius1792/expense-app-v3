package com.clt.usecase;

import com.clt.domain.commons.UseCase;
import com.clt.domain.group.Group;
import com.clt.domain.group.GroupStore;
import reactor.core.publisher.Flux;

public class RetrieveGroupPerUserUseCase implements UseCase {

  private final GroupStore groupStore;

  public RetrieveGroupPerUserUseCase(GroupStore groupStore) {
    this.groupStore = groupStore;
  }

  public Flux<Group> retrieveGroups(String personId) {
    return groupStore.retrieveByMember(personId);
  }
}
