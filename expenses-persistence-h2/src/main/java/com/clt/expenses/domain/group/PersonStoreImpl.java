package com.clt.expenses.domain.group;

import com.clt.domain.group.Person;
import com.clt.domain.group.PersonStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonStoreImpl implements PersonStore {
    @Override
    public Mono<Person> store(Person person) {
        return null;
    }

    @Override
    public Mono<Person> retrieve(String id) {
        return null;
    }

    @Override
    public Flux<Person> retrieve(Iterable<String> ids) {
        return null;
    }
}
