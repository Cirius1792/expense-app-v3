package com.clt.domain.commons;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Store<T, ID> {
  Mono<T> store(T domain);

  Mono<T> retrieve(ID id);

  default Flux<T> retrieve(Iterable<ID> ids) {
    return Flux.fromIterable(ids).flatMap(this::retrieve);
  }
}
