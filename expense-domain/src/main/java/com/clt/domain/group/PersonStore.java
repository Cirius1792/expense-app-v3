package com.clt.domain.group;

import java.util.List;

public interface PersonStore {

    Person store(Person person);
    Person retrieve(String id);
    List<Person> retrieve(Iterable<String> ids);
}
