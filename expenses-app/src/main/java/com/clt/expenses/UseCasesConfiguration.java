package com.clt.expenses;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseRecord;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.GroupFactory;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonFactory;
import com.clt.domain.group.PersonStore;
import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.domain.ledger.ExpenseSplitter;
import com.clt.event.Notifier;
import com.clt.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    IdFactory idFactory() {
        return new UUIDIdFactory();
    }

    @Bean
    public RetrieveSplitPerExpenseUseCase retrieveSplitPerExpenseUseCase(
            ExpenseChargeStore expenseChargeStore) {
        return new RetrieveSplitPerExpenseUseCase(expenseChargeStore);
    }

    @Bean
    RegisterPersonUseCase registerPersonUseCase(IdFactory idFactory, PersonStore personStore) {
        return new RegisterPersonUseCase(new PersonFactory(idFactory), personStore);
    }

    @Bean
    FindUserUseCase findUserUseCase(PersonStore personStore) {
        return new FindUserUseCase(personStore);
    }

    @Bean
    RetrieveGroupPerUserUseCase retrieveGroupPerUserUseCase(GroupStore groupStore) {
        return new RetrieveGroupPerUserUseCase(groupStore);
    }

    @Bean
    GroupFactory groupFactory(IdFactory idFactory) {
        return new GroupFactory(idFactory);
    }

    @Bean
    CreateGroupUseCase createGroupUseCase(
            GroupFactory groupFactory, PersonStore personStore, GroupStore groupStore) {
        return new CreateGroupUseCase(groupFactory, personStore, groupStore);
    }

    @Bean
    FindGroupUseCase findGroupUseCase(PersonStore personStore, GroupStore groupStore) {
        return new FindGroupUseCase(groupStore, personStore);
    }

    @Bean
    RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase(
            PersonStore personStore, ExpenseChargeStore expenseChargeStore) {
        return new RetrieveUserBalancePerGroupUseCase(personStore, expenseChargeStore);
    }

    @Bean
    ExpenseFactory expenseFactory(IdFactory idFactory) {
        return new ExpenseFactory(idFactory);
    }

    @Bean
    AddExpenseUseCase addExpenseUseCase(
            GroupStore groupStore,
            PersonStore personStore,
            ExpenseFactory expenseFactory,
            ExpenseStore expenseStore,
            Notifier<ExpenseRecord> newExpenseNotifier) {
        return new AddExpenseUseCase(personStore, groupStore, expenseFactory, expenseStore, newExpenseNotifier);
    }

    @Bean
    FindExpenseUseCase findExpenseUseCase(ExpenseStore expenseStore, PersonStore personStore) {
        return new FindExpenseUseCase(expenseStore, personStore);
    }

    @Bean
    FindExpensesPerGroupUseCase findExpensesPerGroupUseCase(
            ExpenseStore expenseStore, PersonStore personStore) {
        return new FindExpensesPerGroupUseCase(expenseStore, personStore);
    }

    @Bean
    SplitExpenseUseCase splitExpenseUseCase(ExpenseChargeStore expenseChargeStore) {
        return new SplitExpenseUseCase(new ExpenseSplitter(new UUIDIdFactory()), expenseChargeStore);
    }

}
