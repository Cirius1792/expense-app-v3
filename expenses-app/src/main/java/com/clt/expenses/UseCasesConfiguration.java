package com.clt.expenses;

import com.clt.domain.commons.IdFactory;
import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseRecord;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.GroupFactory;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.UserFactory;
import com.clt.domain.group.UserStore;
import com.clt.domain.ledger.BalanceService;
import com.clt.domain.ledger.BalanceServiceImpl;
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
    RegisterUserUseCase registerPersonUseCase(IdFactory idFactory, UserStore personStore) {
        return new RegisterUserUseCase(new UserFactory(idFactory), personStore);
    }

    @Bean
    FindUserUseCase findUserUseCase(UserStore personStore) {
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
            GroupFactory groupFactory, UserStore personStore, GroupStore groupStore) {
        return new CreateGroupUseCase(groupFactory, personStore, groupStore);
    }

    @Bean
    FindGroupUseCase findGroupUseCase(UserStore personStore, GroupStore groupStore) {
        return new FindGroupUseCase(groupStore, personStore);
    }

    @Bean
    RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase(
            UserStore personStore, ExpenseChargeStore expenseChargeStore) {
        return new RetrieveUserBalancePerGroupUseCase(personStore, expenseChargeStore);
    }

    @Bean
    ExpenseFactory expenseFactory(IdFactory idFactory) {
        return new ExpenseFactory(idFactory);
    }

    @Bean
    AddExpenseUseCase addExpenseUseCase(
            GroupStore groupStore,
            UserStore personStore,
            ExpenseFactory expenseFactory,
            ExpenseStore expenseStore,
            Notifier<ExpenseRecord> newExpenseNotifier) {
        return new AddExpenseUseCase(personStore, groupStore, expenseFactory, expenseStore, newExpenseNotifier);
    }

    @Bean
    FindExpenseUseCase findExpenseUseCase(ExpenseStore expenseStore, UserStore personStore) {
        return new FindExpenseUseCase(expenseStore, personStore);
    }

    @Bean
    FindExpensesPerGroupUseCase findExpensesPerGroupUseCase(
            ExpenseStore expenseStore, UserStore personStore) {
        return new FindExpensesPerGroupUseCase(expenseStore, personStore);
    }

    @Bean
    SplitExpenseUseCase splitExpenseUseCase(ExpenseChargeStore expenseChargeStore) {
        return new SplitExpenseUseCase(new ExpenseSplitter(new UUIDIdFactory()), expenseChargeStore);
    }
    @Bean
    AddMembersToAGroupUseCase addMembersToAGroupUseCase(GroupStore groupStore, UserStore personStore, FindGroupUseCase findGroupUseCase){
        return new AddMembersToAGroupUseCase(personStore, groupStore, findGroupUseCase);
    }

    @Bean
    BalanceService balanceService(ExpenseChargeStore expenseChargeStore){
        return new BalanceServiceImpl(expenseChargeStore);
    }
    @Bean
    PayUseCase payUseCase(IdFactory idFactory, ExpenseChargeStore expenseChargeStore, BalanceService balanceService){
        return new PayUseCase(idFactory, expenseChargeStore, balanceService);
    }
}
