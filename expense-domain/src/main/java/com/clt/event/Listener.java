package com.clt.event;

import reactor.core.publisher.Mono;

public interface Listener <T>{
    Mono<Void> receive(GenericEvent<T> event);
}
