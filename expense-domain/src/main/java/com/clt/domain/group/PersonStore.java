package com.clt.domain.group;

public interface PersonStore {

    Person store(Person person);
    Person retrieve(String id);
    Person retrieve(Iterable<String> ids);
}
