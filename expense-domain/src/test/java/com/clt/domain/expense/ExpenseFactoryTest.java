package com.clt.domain.expense;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.User;
import com.clt.domain.group.PersonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpenseFactoryTest {

  private static final Money EXPENSE_AMOUNT = Money.euros(12.2);
  private static final String EXPENSE_DESCRIPTION = "Milk";
  private static final User EXPENSE_OWNER = PersonUtil.newPerson();
  private static final String EXPENSE_GROUP = "group-id";
  ExpenseFactory expenseFactory = new ExpenseFactory(new UUIDIdFactory());

  @DisplayName(
      "When an expense is created "
          + "Then the fields owner, amount, group id and description are set"
          + "And the id is not null")
  @Test
  void test_expense_creation_basic_fields() {
    Expense actual =
        expenseFactory.create(
            EXPENSE_DESCRIPTION, EXPENSE_AMOUNT, EXPENSE_OWNER.getId(), EXPENSE_GROUP);
    Assertions.assertNotNull(actual.getId());
    Assertions.assertEquals(EXPENSE_DESCRIPTION, actual.getDescription());
    Assertions.assertEquals(EXPENSE_OWNER.getId(), actual.getOwner());
    Assertions.assertEquals(EXPENSE_AMOUNT, actual.getAmount());
    Assertions.assertEquals(EXPENSE_GROUP, actual.getGroupId());
  }
}
