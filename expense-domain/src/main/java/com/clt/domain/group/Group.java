package com.clt.domain.group;

import org.immutables.value.Value;

import java.util.Set;

@Value.Immutable
public interface Group {
    String id();
    String name();
    Person owner();
    Set<Person> members();
}
