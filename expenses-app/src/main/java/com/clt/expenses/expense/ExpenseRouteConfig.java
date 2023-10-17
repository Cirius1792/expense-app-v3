package com.clt.expenses.expense;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.expense.ExpenseFactory;
import com.clt.domain.expense.ExpenseStore;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonStore;
import com.clt.usecase.AddExpenseUseCase;
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
  RouterFunction<ServerResponse> expensesRouter(
      ExpenseMapper expenseMapper, AddExpenseUseCase addExpenseUseCase) {
    return new ExpenseRouter(expenseMapper, addExpenseUseCase).createRoutes();
  }
}
