package com.clt.domain.group;

import com.clt.domain.commons.UUIDIdFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PersonFactoryTest {
  private static final String USER_NAME = "Mario";
  private final PersonFactory personFactory = new PersonFactory(new UUIDIdFactory());

  @DisplayName(
      "Given a valid name"
          + "When creating a person "
          + "Then a new Person is created with an unique Id and a name")
  @Test
  void test_create_person_with_name_and_id() {
    Person actual = personFactory.create(USER_NAME);
    Assertions.assertNotNull(actual.id(), "User id should not be null");
    Assertions.assertEquals(USER_NAME, actual.username(), "Username does not match");
  }
}
