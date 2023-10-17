package com.clt.expenses.domain.expense;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("EXPENSE")
public class ExpenseEntity implements Persistable<String> {
  @Id private String id;
  private String description;
  private String amount;
  private String ownerId;
  private String groupId;
  @Transient private boolean isNew = true;

  public void setNew(boolean aNew) {
    isNew = aNew;
  }

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  @Override
  public String toString() {
    return "ExpenseEntity{"
        + "id='"
        + id
        + '\''
        + ", description='"
        + description
        + '\''
        + ", amount="
        + amount
        + ", personId='"
        + ownerId
        + '\''
        + ", groupId='"
        + groupId
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExpenseEntity that = (ExpenseEntity) o;
    return Objects.equals(id, that.id)
        && Objects.equals(description, that.description)
        && Objects.equals(amount, that.amount)
        && Objects.equals(ownerId, that.ownerId)
        && Objects.equals(groupId, that.groupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, amount, ownerId, groupId);
  }
}
