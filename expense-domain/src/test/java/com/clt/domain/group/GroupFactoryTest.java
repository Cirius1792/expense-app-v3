package com.clt.domain.group;

import com.clt.domain.commons.UUIDIdFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupFactoryTest {

  private static final String GROUP_NAME = "group-x";
  private static final String GROUP_OWNER = "owner";
  private static final Group GROUP = GroupUtil.newGroup();

  GroupFactory service = new GroupFactory(new UUIDIdFactory());

  @DisplayName(
      "Given a name and an owner "
          + "When creating the group "
          + "Then A group with the given name and owner is created "
          + "And the group id is not null")
  @Test
  void create_new_group_test() {
    Group actual = service.create(GROUP_NAME, GROUP_OWNER);
    Assertions.assertNotNull(actual);
    Assertions.assertNotNull(actual.id(), "Missing id");
    Assertions.assertEquals(GROUP_NAME, actual.name(), "Wrong group name");
    Assertions.assertEquals(GROUP_OWNER, actual.owner(), "Wrong owner");
  }

  @DisplayName(
      "When creating a group " + "Then the owner of the group is also a member of the group")
  @Test
  void owner_of_the_group_should_be_a_member() {
    Group actual = service.create(GROUP_NAME, GROUP_OWNER);
    Assertions.assertTrue(
        actual.members().contains(GROUP_OWNER), "The owner of the group should also be a member");
  }

  @DisplayName(
      "Given a group and a user not in the group "
          + "When adding the user to the group "
          + "Then the user is a member of the group")
  @Test
  void add_user_to_a_group_test() {
    String newPersonId = "new-id";
    Group actual = service.add(GROUP, newPersonId);
    Assertions.assertNotNull(actual);
    Assertions.assertTrue(actual.members().contains(newPersonId), "Missing expected group member");
  }
}
