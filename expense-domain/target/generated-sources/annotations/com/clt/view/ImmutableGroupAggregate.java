package com.clt.view;

import com.clt.domain.group.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link GroupAggregate}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableGroupAggregate.builder()}.
 */
@Generated(from = "GroupAggregate", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
final class ImmutableGroupAggregate implements GroupAggregate {
  private final String id;
  private final String name;
  private final Person owner;
  private final Set<Person> members;

  private ImmutableGroupAggregate(
      String id,
      String name,
      Person owner,
      Set<Person> members) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.members = members;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public String id() {
    return id;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * @return The value of the {@code owner} attribute
   */
  @Override
  public Person owner() {
    return owner;
  }

  /**
   * @return The value of the {@code members} attribute
   */
  @Override
  public Set<Person> members() {
    return members;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GroupAggregate#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGroupAggregate withId(String value) {
    String newValue = Objects.requireNonNull(value, "id");
    if (this.id.equals(newValue)) return this;
    return new ImmutableGroupAggregate(newValue, this.name, this.owner, this.members);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GroupAggregate#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGroupAggregate withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableGroupAggregate(this.id, newValue, this.owner, this.members);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GroupAggregate#owner() owner} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for owner
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGroupAggregate withOwner(Person value) {
    if (this.owner == value) return this;
    Person newValue = Objects.requireNonNull(value, "owner");
    return new ImmutableGroupAggregate(this.id, this.name, newValue, this.members);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link GroupAggregate#members() members}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableGroupAggregate withMembers(Person... elements) {
    Set<Person> newValue = createUnmodifiableSet(createSafeList(Arrays.asList(elements), true, false));
    return new ImmutableGroupAggregate(this.id, this.name, this.owner, newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link GroupAggregate#members() members}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of members elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableGroupAggregate withMembers(Iterable<? extends Person> elements) {
    if (this.members == elements) return this;
    Set<Person> newValue = createUnmodifiableSet(createSafeList(elements, true, false));
    return new ImmutableGroupAggregate(this.id, this.name, this.owner, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableGroupAggregate} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableGroupAggregate
        && equalTo(0, (ImmutableGroupAggregate) another);
  }

  private boolean equalTo(int synthetic, ImmutableGroupAggregate another) {
    return id.equals(another.id)
        && name.equals(another.name)
        && owner.equals(another.owner)
        && members.equals(another.members);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code owner}, {@code members}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + id.hashCode();
    h += (h << 5) + name.hashCode();
    h += (h << 5) + owner.hashCode();
    h += (h << 5) + members.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code GroupAggregate} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "GroupAggregate{"
        + "id=" + id
        + ", name=" + name
        + ", owner=" + owner
        + ", members=" + members
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link GroupAggregate} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable GroupAggregate instance
   */
  public static ImmutableGroupAggregate copyOf(GroupAggregate instance) {
    if (instance instanceof ImmutableGroupAggregate) {
      return (ImmutableGroupAggregate) instance;
    }
    return ImmutableGroupAggregate.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableGroupAggregate ImmutableGroupAggregate}.
   * <pre>
   * ImmutableGroupAggregate.builder()
   *    .id(String) // required {@link GroupAggregate#id() id}
   *    .name(String) // required {@link GroupAggregate#name() name}
   *    .owner(com.clt.domain.group.Person) // required {@link GroupAggregate#owner() owner}
   *    .addMembers|addAllMembers(com.clt.domain.group.Person) // {@link GroupAggregate#members() members} elements
   *    .build();
   * </pre>
   * @return A new ImmutableGroupAggregate builder
   */
  public static ImmutableGroupAggregate.Builder builder() {
    return new ImmutableGroupAggregate.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableGroupAggregate ImmutableGroupAggregate}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "GroupAggregate", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_NAME = 0x2L;
    private static final long INIT_BIT_OWNER = 0x4L;
    private long initBits = 0x7L;

    private String id;
    private String name;
    private Person owner;
    private List<Person> members = new ArrayList<Person>();

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code GroupAggregate} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * Collection elements and entries will be added, not replaced.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(GroupAggregate instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      owner(instance.owner());
      addAllMembers(instance.members());
      return this;
    }

    /**
     * Initializes the value for the {@link GroupAggregate#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(String id) {
      this.id = Objects.requireNonNull(id, "id");
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link GroupAggregate#name() name} attribute.
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      initBits &= ~INIT_BIT_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link GroupAggregate#owner() owner} attribute.
     * @param owner The value for owner 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder owner(Person owner) {
      this.owner = Objects.requireNonNull(owner, "owner");
      initBits &= ~INIT_BIT_OWNER;
      return this;
    }

    /**
     * Adds one element to {@link GroupAggregate#members() members} set.
     * @param element A members element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addMembers(Person element) {
      this.members.add(Objects.requireNonNull(element, "members element"));
      return this;
    }

    /**
     * Adds elements to {@link GroupAggregate#members() members} set.
     * @param elements An array of members elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addMembers(Person... elements) {
      for (Person element : elements) {
        this.members.add(Objects.requireNonNull(element, "members element"));
      }
      return this;
    }


    /**
     * Sets or replaces all elements for {@link GroupAggregate#members() members} set.
     * @param elements An iterable of members elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder members(Iterable<? extends Person> elements) {
      this.members.clear();
      return addAllMembers(elements);
    }

    /**
     * Adds elements to {@link GroupAggregate#members() members} set.
     * @param elements An iterable of members elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllMembers(Iterable<? extends Person> elements) {
      for (Person element : elements) {
        this.members.add(Objects.requireNonNull(element, "members element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableGroupAggregate ImmutableGroupAggregate}.
     * @return An immutable instance of GroupAggregate
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableGroupAggregate build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableGroupAggregate(id, name, owner, createUnmodifiableSet(members));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_OWNER) != 0) attributes.add("owner");
      return "Cannot build GroupAggregate, some of required attributes are not set " + attributes;
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  /** Unmodifiable set constructed from list to avoid rehashing. */
  private static <T> Set<T> createUnmodifiableSet(List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptySet();
    case 1: return Collections.singleton(list.get(0));
    default:
      Set<T> set = new LinkedHashSet<>(list.size());
      set.addAll(list);
      return Collections.unmodifiableSet(set);
    }
  }
}
