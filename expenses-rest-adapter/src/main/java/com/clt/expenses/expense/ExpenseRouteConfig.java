package com.clt.expenses.expense;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonStore;
import com.clt.usecase.AddExpenseUseCase;
import com.clt.usecase.FindExpenseUseCase;
import com.clt.usecase.FindExpensesPerGroupUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ExpenseRouteConfig {
  @Bean
  ExpenseFactory expenseFactory() {
    return new ExpenseFactory(new UUIDIdFactory());
  }

  @Bean
  AddExpenseUseCase addExpenseUseCase(
      GroupStore groupStore,
      PersonStore personStore,
      ExpenseFactory expenseFactory,
      ExpenseStore expenseStore) {
    return new AddExpenseUseCase(personStore, groupStore, expenseFactory, expenseStore);
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
  RouterFunction<ServerResponse> expensesRouter(
      ExpenseMapper expenseMapper,
      AddExpenseUseCase addExpenseUseCase,
      FindExpenseUseCase findExpenseUseCase,
      FindExpensesPerGroupUseCase findExpensesPerGroupUseCase) {
    return new ExpenseRouter(
            expenseMapper, addExpenseUseCase, findExpenseUseCase, findExpensesPerGroupUseCase)
        .createRoutes();
  }
}
