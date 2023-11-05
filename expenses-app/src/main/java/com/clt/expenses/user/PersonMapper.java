package com.clt.expenses.user;

import com.clt.domain.group.Person;
import com.clt.expenses.user.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
  public UserResponseDto toDto(Person user) {
    UserResponseDto response = new UserResponseDto();
    response.setId(user.id());
    response.setUsername(user.username());
    return response;
  }
}
