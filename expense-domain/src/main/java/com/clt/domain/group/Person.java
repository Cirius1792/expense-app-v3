package com.clt.domain.group;

import org.immutables.value.Value;

@Value.Immutable
public interface Person {
  String id();

  @Value.Auxiliary
  String username();
}