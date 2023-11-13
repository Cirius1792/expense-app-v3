package com.clt.expenses.domain.group;

import com.clt.domain.group.User;
import com.clt.domain.group.UserStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserStoreImpl implements UserStore {
  private final UserRepository personRepository;
  private final PersonPersistenceMapper personMapper;

  public UserStoreImpl(UserRepository personRepository, PersonPersistenceMapper personMapper) {
    this.personRepository = personRepository;
    this.personMapper = personMapper;
  }

  @Override
  public Mono<User> store(User user) {
    UserEntity entity = personMapper.toEntity(user);
    return this.personRepository
        .findById(user.getId())
        .map(this::setAsNotNew)
        .switchIfEmpty(Mono.just(entity))
        .flatMap(personRepository::save)
        .map(personMapper::toDomain);
  }

  private UserEntity setAsNotNew(UserEntity entity) {
    entity.setNew(false);
    return entity;
  }

  @Override
  public Mono<User> retrieve(String id) {
    return personRepository.findById(id).map(personMapper::toDomain);
  }

  @Override
  public Flux<User> retrieve(Iterable<String> ids) {
    return personRepository.findAllById(ids).map(personMapper::toDomain);
  }
}
