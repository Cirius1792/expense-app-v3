package com.clt.expenses.domain.group;

import com.clt.domain.group.ImmutableUser;
import com.clt.domain.group.User;
import com.clt.expenses.domain.common.PersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonPersistenceMapper implements PersistenceMapper<UserEntity, User> {
  @Override
  public UserEntity toEntity(User user) {
    UserEntity entity = new UserEntity();
    entity.setId(user.getId());
    entity.setUsername(user.getUsername());
    return entity;
  }

  @Override
  public User toDomain(UserEntity userEntity) {
    return ImmutableUser.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .build();
  }
}
