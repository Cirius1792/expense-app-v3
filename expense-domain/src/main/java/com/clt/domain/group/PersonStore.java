package com.clt.domain.group;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonStore {

    Mono<Person> store(Person person);
    Mono<Person> retrieve(String id);
    Flux<Person> retrieve(Iterable<String> ids);
}
