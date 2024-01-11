package com.clt.expenses.domain.group;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("Person")
public class UserEntity implements Persistable<String> {
  @Id private String id;
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
  @Override
  public boolean isNew() {
    return isNew;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
            "id='" + id + '\'' +
            ", isNew=" + isNew +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserEntity that = (UserEntity) o;
    return isNew == that.isNew && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
