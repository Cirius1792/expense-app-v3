package com.clt.usecase;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.group.ImmutablePerson;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonStore;

public class RegisterPerson {

    private final IdFactory idFactory;
    private final PersonStore store;

    public RegisterPerson(IdFactory idFactory, PersonStore store) {
        this.idFactory = idFactory;
        this.store = store;
    }

    public Person register(String username){
        return this.store.store(ImmutablePerson.builder()
                .id(idFactory.newId())
                .username(username)
                .build());
    }
}
