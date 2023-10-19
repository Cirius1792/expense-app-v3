package com.clt.expenses.domain.group;

import com.clt.domain.group.Group;
import com.clt.domain.group.ImmutableGroup;
import org.springframework.stereotype.Component;

@Component
public class GroupPersistenceMapper {
    public GroupEntity toEntity(Group group) {
        return new GroupEntity(group.id(), group.name(), group.owner(), group.members());
    }

    public Group toDomain(GroupEntity entity) {
        return ImmutableGroup.builder()
                .id(entity.getId())
                .name(entity.getName())
                .owner(entity.getOwner())
                .members(entity.getMembers())
                .build();
    }
}
