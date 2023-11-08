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


INSERT INTO expense (id, description, amount, owner_id, group_id)
    VALUES(1, 'Milk', '4.99', 'member-1-id', 'test-group');
INSERT INTO expense (id, description, amount, owner_id, group_id)
    VALUES(2, 'Water', '2.99', 'member-2-id', 'test-group');

INSERT INTO expense_charge (id, expense, group_id, due_amount, debtor, creditor)
  VALUES(1, 1, 'test-group', '1.50', 'member-1-id', 'member-2-id');
INSERT INTO expense_charge (id, expense, group_id, due_amount, debtor, creditor)
  VALUES(2, 2, 'test-group', '1.00', 'member-2-id', 'member-1-id');

