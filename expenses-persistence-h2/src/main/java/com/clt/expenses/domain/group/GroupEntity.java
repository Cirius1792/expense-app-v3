package com.clt.expenses.domain.group;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("group")
public class GroupEntity implements Persistable<String> {
  @Id private String id;
  private String name;
  private String owner;
  private List<String> members;

  public GroupEntity(String id, String name, String owner) {
    this.id = id;
    this.name = name;
    this.owner = owner;
  }

  public GroupEntity(String id, String name, String owner, List<String> members) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.members = members;
  }

  @Transient private boolean isNew = true;

  public String getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return isNew;
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

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public List<String> getMembers() {
    return members;
  }

  public void setMembers(List<String> members) {
    this.members = members;
  }
}
