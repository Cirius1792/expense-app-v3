package com.clt.view;

import com.clt.domain.expense.Money;
import com.clt.domain.group.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link ExpenseAggregate}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableExpenseAggregate.builder()}.
 */
@Generated(from = "ExpenseAggregate", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
final class ImmutableExpenseAggregate implements ExpenseAggregate {
  private final String id;
  private final String description;
  private final Money amount;
  private final Person owner;
  private final String groupId;

  private ImmutableExpenseAggregate(
      String id,
      String description,
      Money amount,
      Person owner,
      String groupId) {
    this.id = id;
    this.description = description;
    this.amount = amount;
    this.owner = owner;
    this.groupId = groupId;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public String id() {
    return id;
  }

  /**
   * @return The value of the {@code description} attribute
   */
  @Override
  public String description() {
    return description;
  }

  /**
   * @return The value of the {@code amount} attribute
   */
  @Override
  public Money amount() {
    return amount;
  }

  /**
   * @return The value of the {@code owner} attribute
   */
  @Override
  public Person owner() {
    return owner;
  }

  /**
   * @return The value of the {@code groupId} attribute
   */
  @Override
  public String groupId() {
    return groupId;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseAggregate#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseAggregate withId(String value) {
    String newValue = Objects.requireNonNull(value, "id");
    if (this.id.equals(newValue)) return this;
    return new ImmutableExpenseAggregate(newValue, this.description, this.amount, this.owner, this.groupId);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseAggregate#description() description} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for description
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseAggregate withDescription(String value) {
    String newValue = Objects.requireNonNull(value, "description");
    if (this.description.equals(newValue)) return this;
    return new ImmutableExpenseAggregate(this.id, newValue, this.amount, this.owner, this.groupId);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseAggregate#amount() amount} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for amount
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseAggregate withAmount(Money value) {
    if (this.amount == value) return this;
    Money newValue = Objects.requireNonNull(value, "amount");
    return new ImmutableExpenseAggregate(this.id, this.description, newValue, this.owner, this.groupId);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseAggregate#owner() owner} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for owner
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseAggregate withOwner(Person value) {
    if (this.owner == value) return this;
    Person newValue = Objects.requireNonNull(value, "owner");
    return new ImmutableExpenseAggregate(this.id, this.description, this.amount, newValue, this.groupId);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseAggregate#groupId() groupId} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for groupId
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseAggregate withGroupId(String value) {
    String newValue = Objects.requireNonNull(value, "groupId");
    if (this.groupId.equals(newValue)) return this;
    return new ImmutableExpenseAggregate(this.id, this.description, this.amount, this.owner, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableExpenseAggregate} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableExpenseAggregate
        && equalTo(0, (ImmutableExpenseAggregate) another);
  }

  private boolean equalTo(int synthetic, ImmutableExpenseAggregate another) {
    return id.equals(another.id)
        && description.equals(another.description)
        && amount.equals(another.amount)
        && owner.equals(another.owner)
        && groupId.equals(another.groupId);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code description}, {@code amount}, {@code owner}, {@code groupId}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + description.hashCode();
    h += (h << 5) + amount.hashCode();
    h += (h << 5) + owner.hashCode();
    h += (h << 5) + groupId.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExpenseAggregate} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "ExpenseAggregate{"
        + "id=" + id
        + ", description=" + description
        + ", amount=" + amount
        + ", owner=" + owner
        + ", groupId=" + groupId
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link ExpenseAggregate} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExpenseAggregate instance
   */
  public static ImmutableExpenseAggregate copyOf(ExpenseAggregate instance) {
    if (instance instanceof ImmutableExpenseAggregate) {
      return (ImmutableExpenseAggregate) instance;
    }
    return ImmutableExpenseAggregate.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableExpenseAggregate ImmutableExpenseAggregate}.
   * <pre>
   * ImmutableExpenseAggregate.builder()
   *    .id(String) // required {@link ExpenseAggregate#id() id}
   *    .description(String) // required {@link ExpenseAggregate#description() description}
   *    .amount(com.clt.domain.expense.Money) // required {@link ExpenseAggregate#amount() amount}
   *    .owner(com.clt.domain.group.Person) // required {@link ExpenseAggregate#owner() owner}
   *    .groupId(String) // required {@link ExpenseAggregate#groupId() groupId}
   *    .build();
   * </pre>
   * @return A new ImmutableExpenseAggregate builder
   */
  public static ImmutableExpenseAggregate.Builder builder() {
    return new ImmutableExpenseAggregate.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableExpenseAggregate ImmutableExpenseAggregate}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ExpenseAggregate", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_DESCRIPTION = 0x2L;
    private static final long INIT_BIT_AMOUNT = 0x4L;
    private static final long INIT_BIT_OWNER = 0x8L;
    private static final long INIT_BIT_GROUP_ID = 0x10L;
    private long initBits = 0x1fL;

    private String id;
    private String description;
    private Money amount;
    private Person owner;
    private String groupId;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ExpenseAggregate} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ExpenseAggregate instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      description(instance.description());
      amount(instance.amount());
      owner(instance.owner());
      groupId(instance.groupId());
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseAggregate#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(String id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseAggregate#description() description} attribute.
     * @param description The value for description 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder description(String description) {
      this.description = Objects.requireNonNull(description, "description");
      initBits &= ~INIT_BIT_DESCRIPTION;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseAggregate#amount() amount} attribute.
     * @param amount The value for amount 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder amount(Money amount) {
      this.amount = Objects.requireNonNull(amount, "amount");
      initBits &= ~INIT_BIT_AMOUNT;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseAggregate#owner() owner} attribute.
     * @param owner The value for owner 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder owner(Person owner) {
      this.owner = Objects.requireNonNull(owner, "owner");
      initBits &= ~INIT_BIT_OWNER;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseAggregate#groupId() groupId} attribute.
     * @param groupId The value for groupId 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder groupId(String groupId) {
      this.groupId = Objects.requireNonNull(groupId, "groupId");
      initBits &= ~INIT_BIT_GROUP_ID;
      return this;
    }

    /**
     * Builds a new {@link ImmutableExpenseAggregate ImmutableExpenseAggregate}.
     * @return An immutable instance of ExpenseAggregate
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableExpenseAggregate build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableExpenseAggregate(id, description, amount, owner, groupId);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_DESCRIPTION) != 0) attributes.add("description");
      if ((initBits & INIT_BIT_AMOUNT) != 0) attributes.add("amount");
      if ((initBits & INIT_BIT_OWNER) != 0) attributes.add("owner");
      if ((initBits & INIT_BIT_GROUP_ID) != 0) attributes.add("groupId");
      return "Cannot build ExpenseAggregate, some of required attributes are not set " + attributes;
    }
  }
}
