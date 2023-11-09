package com.clt.domain.view;

import com.clt.domain.group.Person;
import java.util.Set;
import org.immutables.value.Value;

@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE)
@Value.Immutable
public interface GroupAggregate {
  String id();

  String name();

  Person owner();

  Set<Person> members();
}
