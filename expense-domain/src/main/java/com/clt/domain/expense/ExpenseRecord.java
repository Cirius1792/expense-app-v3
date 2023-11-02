package com.clt.domain.expense;

import com.clt.domain.group.Group;
import com.clt.domain.group.Person;

public record ExpenseRecord(Person expenseOwner, Expense expense, Group group) {}
