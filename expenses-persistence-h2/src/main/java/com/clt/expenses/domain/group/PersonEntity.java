package com.clt.expenses.domain.group;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("Person")
public class PersonEntity implements Persistable<String> {
  @Id private String id;
  private String username;
  @Transient private boolean isNew = true;

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setNew(boolean aNew) {
    isNew = aNew;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isNew() {
    return isNew;
  }

  @Override
  public String toString() {
    return "PersonEntity{"
        + "id='"
        + id
        + '\''
        + ", username='"
        + username
        + '\''
        + ", isNew="
        + isNew
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonEntity that = (PersonEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username);
  }
}
