package com.clt.event;

import reactor.core.publisher.Mono;

public interface Notifier<T> {

  Mono<GenericEvent<T>> notify(T event);
}
