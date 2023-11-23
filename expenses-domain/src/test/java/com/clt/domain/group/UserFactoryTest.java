package com.clt.domain.group;

import com.clt.domain.commons.UUIDIdFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserFactoryTest {
  private static final String USER_NAME = "Mario";
  private final UserFactory userFactory = new UserFactory(new UUIDIdFactory());

  @DisplayName(
      "Given a valid name"
          + "When creating a person "
          + "Then a new Person is created with an unique Id and a name")
  @Test
  void test_create_person_with_name_and_id() {
    User actual = userFactory.create(USER_NAME);
    Assertions.assertNotNull(actual.getId(), "User id should not be null");
    Assertions.assertEquals(USER_NAME, actual.getUsername(), "Username does not match");
  }
}
