package com.clt.domain.group;

public interface GroupStore {
    Group store(Group group);
    Group retrieve(String groupId);
}
