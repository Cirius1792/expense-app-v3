package com.clt.usecase;

import com.clt.domain.group.Person;
import com.clt.domain.group.PersonFactory;
import com.clt.domain.group.PersonStore;
import reactor.core.publisher.Mono;

public class RegisterPersonUseCase {

    private final PersonFactory personFactory;
    private final PersonStore store;

    public RegisterPersonUseCase(PersonFactory personFactory, PersonStore store) {
        this.personFactory = personFactory;
        this.store = store;
    }

    public Mono<Person> register(String username){
        return this.store.store(personFactory.create(username));
    }
}
