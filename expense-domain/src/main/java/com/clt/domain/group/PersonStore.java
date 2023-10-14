package com.clt.domain.group;

import java.util.List;
import java.util.Optional;

public interface PersonStore {

    Person store(Person person);
    Optional<Person> retrieve(String id);
    List<Person> retrieve(Iterable<String> ids);
}
