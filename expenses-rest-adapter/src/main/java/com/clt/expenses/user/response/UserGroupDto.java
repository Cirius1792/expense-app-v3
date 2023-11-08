package com.clt.expenses.user.response;

import java.util.List;

public class UserGroupDto {
  String id;
  String name;
  String ownerId;
  List<String> membersIds;

  @Override
  public String toString() {
    return "UserGroupDto{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", ownerId='"
        + ownerId
        + '\''
        + ", membersIds="
        + membersIds
        + '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public List<String> getMembersIds() {
    return membersIds;
  }

  public void setMembersIds(List<String> membersIds) {
    this.membersIds = membersIds;
  }
}
