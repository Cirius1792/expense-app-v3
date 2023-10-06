package com.clt.domain.group;

import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
public interface Group {
  String id();

  String name();

  Person owner();

  Set<Person> members();
}
