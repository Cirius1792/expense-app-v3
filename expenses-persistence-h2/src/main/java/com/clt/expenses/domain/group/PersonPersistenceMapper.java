package com.clt.expenses.domain.group;

import com.clt.domain.group.ImmutablePerson;
import com.clt.domain.group.Person;
import com.clt.expenses.domain.common.PersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonPersistenceMapper implements PersistenceMapper<PersonEntity, Person> {
    @Override
    public PersonEntity toEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.id());
        entity.setUsername(person.username());
        return entity;
    }

    @Override
    public Person toDomain(PersonEntity personEntity) {
        return ImmutablePerson.builder()
                .id(personEntity.getId())
                .username(personEntity.getUsername())
                .build();
    }
}
