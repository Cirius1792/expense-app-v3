package com.clt.domain.ledger;

import com.clt.domain.expense.Money;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link ExpenseCharge}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableExpenseCharge.builder()}.
 */
@Generated(from = "ExpenseCharge", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableExpenseCharge implements ExpenseCharge {
  private final String id;
  private final String expense;
  private final String groupId;
  private final Money dueAmount;
  private final String debtor;
  private final String creditor;

  private ImmutableExpenseCharge(
      String id,
      String expense,
      String groupId,
      Money dueAmount,
      String debtor,
      String creditor) {
    this.id = id;
    this.expense = expense;
    this.groupId = groupId;
    this.dueAmount = dueAmount;
    this.debtor = debtor;
    this.creditor = creditor;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public String id() {
    return id;
  }

  /**
   * @return The value of the {@code expense} attribute
   */
  @Override
  public String expense() {
    return expense;
  }

  /**
   * @return The value of the {@code groupId} attribute
   */
  @Override
  public String groupId() {
    return groupId;
  }

  /**
   * @return The value of the {@code dueAmount} attribute
   */
  @Override
  public Money dueAmount() {
    return dueAmount;
  }

  /**
   * @return The value of the {@code debtor} attribute
   */
  @Override
  public String debtor() {
    return debtor;
  }

  /**
   * @return The value of the {@code creditor} attribute
   */
  @Override
  public String creditor() {
    return creditor;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseCharge#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseCharge withId(String value) {
    String newValue = Objects.requireNonNull(value, "id");
    if (this.id.equals(newValue)) return this;
    return new ImmutableExpenseCharge(newValue, this.expense, this.groupId, this.dueAmount, this.debtor, this.creditor);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseCharge#expense() expense} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for expense
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseCharge withExpense(String value) {
    String newValue = Objects.requireNonNull(value, "expense");
    if (this.expense.equals(newValue)) return this;
    return new ImmutableExpenseCharge(this.id, newValue, this.groupId, this.dueAmount, this.debtor, this.creditor);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseCharge#groupId() groupId} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for groupId
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseCharge withGroupId(String value) {
    String newValue = Objects.requireNonNull(value, "groupId");
    if (this.groupId.equals(newValue)) return this;
    return new ImmutableExpenseCharge(this.id, this.expense, newValue, this.dueAmount, this.debtor, this.creditor);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseCharge#dueAmount() dueAmount} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for dueAmount
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseCharge withDueAmount(Money value) {
    if (this.dueAmount == value) return this;
    Money newValue = Objects.requireNonNull(value, "dueAmount");
    return new ImmutableExpenseCharge(this.id, this.expense, this.groupId, newValue, this.debtor, this.creditor);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseCharge#debtor() debtor} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for debtor
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseCharge withDebtor(String value) {
    String newValue = Objects.requireNonNull(value, "debtor");
    if (this.debtor.equals(newValue)) return this;
    return new ImmutableExpenseCharge(this.id, this.expense, this.groupId, this.dueAmount, newValue, this.creditor);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ExpenseCharge#creditor() creditor} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for creditor
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableExpenseCharge withCreditor(String value) {
    String newValue = Objects.requireNonNull(value, "creditor");
    if (this.creditor.equals(newValue)) return this;
    return new ImmutableExpenseCharge(this.id, this.expense, this.groupId, this.dueAmount, this.debtor, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableExpenseCharge} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableExpenseCharge
        && equalTo(0, (ImmutableExpenseCharge) another);
  }

  private boolean equalTo(int synthetic, ImmutableExpenseCharge another) {
    return id.equals(another.id)
        && expense.equals(another.expense)
        && groupId.equals(another.groupId)
        && dueAmount.equals(another.dueAmount)
        && debtor.equals(another.debtor)
        && creditor.equals(another.creditor);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code expense}, {@code groupId}, {@code dueAmount}, {@code debtor}, {@code creditor}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + expense.hashCode();
    h += (h << 5) + groupId.hashCode();
    h += (h << 5) + dueAmount.hashCode();
    h += (h << 5) + debtor.hashCode();
    h += (h << 5) + creditor.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ExpenseCharge} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "ExpenseCharge{"
        + "id=" + id
        + ", expense=" + expense
        + ", groupId=" + groupId
        + ", dueAmount=" + dueAmount
        + ", debtor=" + debtor
        + ", creditor=" + creditor
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link ExpenseCharge} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ExpenseCharge instance
   */
  public static ImmutableExpenseCharge copyOf(ExpenseCharge instance) {
    if (instance instanceof ImmutableExpenseCharge) {
      return (ImmutableExpenseCharge) instance;
    }
    return ImmutableExpenseCharge.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableExpenseCharge ImmutableExpenseCharge}.
   * <pre>
   * ImmutableExpenseCharge.builder()
   *    .id(String) // required {@link ExpenseCharge#id() id}
   *    .expense(String) // required {@link ExpenseCharge#expense() expense}
   *    .groupId(String) // required {@link ExpenseCharge#groupId() groupId}
   *    .dueAmount(com.clt.domain.expense.Money) // required {@link ExpenseCharge#dueAmount() dueAmount}
   *    .debtor(String) // required {@link ExpenseCharge#debtor() debtor}
   *    .creditor(String) // required {@link ExpenseCharge#creditor() creditor}
   *    .build();
   * </pre>
   * @return A new ImmutableExpenseCharge builder
   */
  public static ImmutableExpenseCharge.Builder builder() {
    return new ImmutableExpenseCharge.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableExpenseCharge ImmutableExpenseCharge}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ExpenseCharge", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_EXPENSE = 0x2L;
    private static final long INIT_BIT_GROUP_ID = 0x4L;
    private static final long INIT_BIT_DUE_AMOUNT = 0x8L;
    private static final long INIT_BIT_DEBTOR = 0x10L;
    private static final long INIT_BIT_CREDITOR = 0x20L;
    private long initBits = 0x3fL;

    private String id;
    private String expense;
    private String groupId;
    private Money dueAmount;
    private String debtor;
    private String creditor;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ExpenseCharge} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ExpenseCharge instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      expense(instance.expense());
      groupId(instance.groupId());
      dueAmount(instance.dueAmount());
      debtor(instance.debtor());
      creditor(instance.creditor());
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseCharge#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(String id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseCharge#expense() expense} attribute.
     * @param expense The value for expense 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder expense(String expense) {
      this.expense = Objects.requireNonNull(expense, "expense");
      initBits &= ~INIT_BIT_EXPENSE;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseCharge#groupId() groupId} attribute.
     * @param groupId The value for groupId 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder groupId(String groupId) {
      this.groupId = Objects.requireNonNull(groupId, "groupId");
      initBits &= ~INIT_BIT_GROUP_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseCharge#dueAmount() dueAmount} attribute.
     * @param dueAmount The value for dueAmount 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder dueAmount(Money dueAmount) {
      this.dueAmount = Objects.requireNonNull(dueAmount, "dueAmount");
      initBits &= ~INIT_BIT_DUE_AMOUNT;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseCharge#debtor() debtor} attribute.
     * @param debtor The value for debtor 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder debtor(String debtor) {
      this.debtor = Objects.requireNonNull(debtor, "debtor");
      initBits &= ~INIT_BIT_DEBTOR;
      return this;
    }

    /**
     * Initializes the value for the {@link ExpenseCharge#creditor() creditor} attribute.
     * @param creditor The value for creditor 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder creditor(String creditor) {
      this.creditor = Objects.requireNonNull(creditor, "creditor");
      initBits &= ~INIT_BIT_CREDITOR;
      return this;
    }

    /**
     * Builds a new {@link ImmutableExpenseCharge ImmutableExpenseCharge}.
     * @return An immutable instance of ExpenseCharge
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableExpenseCharge build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableExpenseCharge(id, expense, groupId, dueAmount, debtor, creditor);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_EXPENSE) != 0) attributes.add("expense");
      if ((initBits & INIT_BIT_GROUP_ID) != 0) attributes.add("groupId");
      if ((initBits & INIT_BIT_DUE_AMOUNT) != 0) attributes.add("dueAmount");
      if ((initBits & INIT_BIT_DEBTOR) != 0) attributes.add("debtor");
      if ((initBits & INIT_BIT_CREDITOR) != 0) attributes.add("creditor");
      return "Cannot build ExpenseCharge, some of required attributes are not set " + attributes;
    }
  }
}
