package com.clt.expenses.domain.group;

import com.clt.domain.group.Person;
import com.clt.domain.group.PersonStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonStoreImpl implements PersonStore {
  private final PersonRepository personRepository;
  private final PersonPersistenceMapper personMapper;

  public PersonStoreImpl(PersonRepository personRepository, PersonPersistenceMapper personMapper) {
    this.personRepository = personRepository;
    this.personMapper = personMapper;
  }

  @Override
  public Mono<Person> store(Person person) {
    PersonEntity entity = personMapper.toEntity(person);
    return this.personRepository
        .findById(person.id())
        .map(this::setAsNew)
        .switchIfEmpty(Mono.just(entity))
        .flatMap(personRepository::save)
        .map(personMapper::toDomain);
  }

  private PersonEntity setAsNew(PersonEntity entity) {
    entity.setNew(false);
    return entity;
  }

  @Override
  public Mono<Person> retrieve(String id) {
    return personRepository.findById(id).map(personMapper::toDomain);
  }

  @Override
  public Flux<Person> retrieve(Iterable<String> ids) {
    return personRepository.findAllById(ids).map(personMapper::toDomain);
  }
}
