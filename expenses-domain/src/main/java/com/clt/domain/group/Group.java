package com.clt.domain.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableGroup.class)
@JsonDeserialize(as = ImmutableGroup.class)
public interface Group {
  String getId();

  String getName();

  String getOwner();

  List<String> getMembers();
}
