package com.clt.domain.registry;

import org.immutables.value.Value;

@Value.Immutable
public interface RegisteredUser {
   String getId();
   String getPassword();
}
