package com.clt.expenses.user;

import com.clt.domain.group.Group;
import com.clt.expenses.user.response.UserGroupDto;
import org.springframework.stereotype.Component;

@Component
public class PersonGroupMapper {
  UserGroupDto toDto(Group group) {
    UserGroupDto dto = new UserGroupDto();
    dto.setId(group.getId());
    dto.setName(group.getName());
    dto.setOwnerId(group.getOwner());
    dto.setMembersIds(group.getMembers());
    return dto;
  }
}
