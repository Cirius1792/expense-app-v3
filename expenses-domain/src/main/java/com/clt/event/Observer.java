package com.clt.event;

import reactor.core.publisher.Mono;

public interface Observer<T> {

  Mono<GenericEvent<T>> notify(T event);
}
