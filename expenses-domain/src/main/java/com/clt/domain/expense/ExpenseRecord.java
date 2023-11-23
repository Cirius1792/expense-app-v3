package com.clt.domain.expense;

import com.clt.domain.group.Group;
import com.clt.domain.group.User;

public record ExpenseRecord(User expenseOwner, Expense expense, Group group) {}
