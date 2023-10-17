package com.clt.expenses.group;

import com.clt.domain.group.Group;
import com.clt.expenses.group.response.GroupResponse;
import com.clt.expenses.user.PersonMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
  private final PersonMapper personMapper;

  public GroupMapper(PersonMapper personMapper) {
    this.personMapper = personMapper;
  }

  public GroupResponse toDto(Group group) {
    GroupResponse response = new GroupResponse();
    response.setId(group.id());
    response.setName(group.name());
    response.setOwner(personMapper.toDto(group.owner()));
    response.setMembers(group.members().stream().map(personMapper::toDto).toList());
    return response;
  }
}
