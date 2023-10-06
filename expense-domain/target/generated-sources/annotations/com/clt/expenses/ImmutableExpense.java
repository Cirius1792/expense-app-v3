package com.clt.expenses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.Money;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link Expense}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableExpense.builder()}.
 */
@Generated(from = "Expense", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableExpense implements Expense {
  private final String id;
  private final String description;
  private final Money amount;

  private ImmutableExpense(String id, String description, Money amount) {
    this.id = id;
    this.description = description;
    this.amount = amount;
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
   * Copy the current immutable object by setting a value for the {@link Expense#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpense withId(String value) {
    String newValue = Objects.requireNonNull(value, "id");
    if (this.id.equals(newValue)) return this;
    return new ImmutableExpense(newValue, this.description, this.amount);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Expense#description() description} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for description
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpense withDescription(String value) {
    String newValue = Objects.requireNonNull(value, "description");
    if (this.description.equals(newValue)) return this;
    return new ImmutableExpense(this.id, newValue, this.amount);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Expense#amount() amount} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for amount
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpense withAmount(Money value) {
    if (this.amount == value) return this;
    Money newValue = Objects.requireNonNull(value, "amount");
    return new ImmutableExpense(this.id, this.description, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableExpense} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableExpense
        && equalTo(0, (ImmutableExpense) another);
  }

  private boolean equalTo(int synthetic, ImmutableExpense another) {
    return id.equals(another.id)
        && description.equals(another.description)
        && amount.equals(another.amount);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code description}, {@code amount}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + description.hashCode();
    h += (h << 5) + amount.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Expense} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "Expense{"
        + "id=" + id
        + ", description=" + description
        + ", amount=" + amount
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link Expense} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Expense instance
   */
  public static ImmutableExpense copyOf(Expense instance) {
    if (instance instanceof ImmutableExpense) {
      return (ImmutableExpense) instance;
    }
    return ImmutableExpense.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableExpense ImmutableExpense}.
   * <pre>
   * ImmutableExpense.builder()
   *    .id(String) // required {@link Expense#id() id}
   *    .description(String) // required {@link Expense#description() description}
   *    .amount(com.clt.spend.expense.Money) // required {@link Expense#amount() amount}
   *    .build();
   * </pre>
   * @return A new ImmutableExpense builder
   */
  public static ImmutableExpense.Builder builder() {
    return new ImmutableExpense.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableExpense ImmutableExpense}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "Expense", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_DESCRIPTION = 0x2L;
    private static final long INIT_BIT_AMOUNT = 0x4L;
    private long initBits = 0x7L;

    private String id;
    private String description;
    private Money amount;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Expense} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Expense instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      description(instance.description());
      amount(instance.amount());
      return this;
    }

    /**
     * Initializes the value for the {@link Expense#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(String id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link Expense#description() description} attribute.
     * @param description The value for description 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder description(String description) {
      this.description = Objects.requireNonNull(description, "description");
      initBits &= ~INIT_BIT_DESCRIPTION;
      return this;
    }

    /**
     * Initializes the value for the {@link Expense#amount() amount} attribute.
     * @param amount The value for amount 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder amount(Money amount) {
      this.amount = Objects.requireNonNull(amount, "amount");
      initBits &= ~INIT_BIT_AMOUNT;
      return this;
    }

    /**
     * Builds a new {@link ImmutableExpense ImmutableExpense}.
     * @return An immutable instance of Expense
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableExpense build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableExpense(id, description, amount);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_DESCRIPTION) != 0) attributes.add("description");
      if ((initBits & INIT_BIT_AMOUNT) != 0) attributes.add("amount");
      return "Cannot build Expense, some of required attributes are not set " + attributes;
    }
  }
}
