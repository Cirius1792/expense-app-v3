package com.clt.expenses.expense;

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
