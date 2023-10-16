package com.clt.expenses.domain.group;

import com.clt.domain.group.Group;
import com.clt.domain.group.GroupStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class GroupStoreImpl implements GroupStore {
    @Override
    public Mono<Group> store(Group group) {
        return null;
    }

    @Override
    public Mono<Group> retrieve(String groupId) {
        return null;
    }
}
