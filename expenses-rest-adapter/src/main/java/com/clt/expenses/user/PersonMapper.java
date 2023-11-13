package com.clt.expenses.user;

import com.clt.domain.group.User;
import com.clt.expenses.user.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
  public UserResponseDto toDto(User user) {
    UserResponseDto response = new UserResponseDto();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    return response;
  }
}
