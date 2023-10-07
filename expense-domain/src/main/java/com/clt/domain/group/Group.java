package com.clt.domain.group;

import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
public interface Group {
  String id();

  String name();

  Person owner();

  List<Person> members();
}
