INSERT INTO person(id, username)
  VALUES('owner-id', 'SuperOwner');
INSERT INTO person(id, username)
  VALUES('member-1-id', 'Member One');
INSERT INTO person(id, username)
  VALUES('member-2-id', 'Member Two');
INSERT INTO expense_group (ID, NAME, OWNER)
  VALUES( 'test-group', 'My Group', 'owner-id');
INSERT INTO group_member (group_id, member )
  VALUES( 'test-group', 'owner-id');
INSERT INTO group_member (group_id, member )
  VALUES( 'test-group', 'member-1-id');
INSERT INTO group_member (group_id, member )
  VALUES( 'test-group', 'member-2-id');
