package com.clt.event;

import java.time.ZonedDateTime;

public record GenericEvent<T>(String id, T event, ZonedDateTime creationDate) {
    public GenericEvent(String id, T event) {
        this(id, event, ZonedDateTime.now());
    }
}
