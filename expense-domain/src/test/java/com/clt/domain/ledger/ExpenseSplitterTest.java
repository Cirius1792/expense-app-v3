package com.clt.domain.ledger;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.Expense;
import com.clt.domain.expense.ImmutableExpense;
import com.clt.domain.expense.Money;
import com.clt.domain.group.Group;
import com.clt.domain.group.GroupUtil;
import com.clt.domain.group.Person;
import com.clt.domain.group.PersonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ExpenseSplitterTest {

    private static final Person AL = PersonUtil.newPerson();
    private static final Person JOHN = PersonUtil.newPerson();
    private static final Person JACK = PersonUtil.newPerson();
    private static final Group GROUP = GroupUtil.newGroup("group", Arrays.asList(AL, JACK, JOHN));
    ExpenseSplitter splitter = new ExpenseSplitter(new UUIDIdFactory());
    @DisplayName("Given a group made of three people: Al, John and Jack" +
            "And given that Al bought the milk for X€" +
            "When splitting the expense within the group " +
            "Then an expense charge of X/3€ from Jack to Al is created" +
            "And an expense charge of X/3€ from John to Al is created")
    @Test
    void split_expense_test(){
        Expense expense = ImmutableExpense.builder()
                .owner(AL)
                .id("x")
                .description("Milk")
                .amount(Money.euros(6))
                .groupId(GROUP.id())
                .build();
        List<ExpenseCharge> actual = splitter.split(expense, GROUP);
        ExpenseCharge split = actual.stream()
                .filter(el -> JACK.equals(el.debtor()))
                .findAny()
                .orElseThrow();
        Assertions.assertEquals(Money.euros(2), split.dueAmount());
        Assertions.assertEquals(AL, split.creditor());

        split = actual.stream()
                .filter(el -> JOHN.equals(el.debtor()))
                .findAny()
                .orElseThrow();
        Assertions.assertEquals(Money.euros(2), split.dueAmount());
        Assertions.assertEquals(AL, split.creditor());
    }

}