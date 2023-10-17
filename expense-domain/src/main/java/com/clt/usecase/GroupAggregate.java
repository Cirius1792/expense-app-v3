package com.clt.usecase;

import com.clt.domain.group.Person;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
public interface GroupAggregate {
  String id();

  String name();

  Person owner();

  List<Person> members();
}
