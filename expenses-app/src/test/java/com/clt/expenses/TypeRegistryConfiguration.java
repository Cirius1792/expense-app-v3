package com.clt.expenses;

import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ImmutableExpense;
import com.clt.domain.expense.Money;
import io.cucumber.java.DataTableType;

import java.util.Map;

public class TypeRegistryConfiguration {
    @DataTableType
    public Expense expenseType(Map<String, String> entry) {
        return ImmutableExpense.builder()
                .id(entry.getOrDefault("id", ""))
                .owner(entry.get("owner"))
                .description(entry.get("description"))
                .groupId(entry.getOrDefault("groupId", ""))
                .amount(Money.euros(entry.get("amount")))
                .build();
    }
}
