package com.clt.expenses.ledger;

import com.clt.domain.ledger.ExpenseChargeStore;
import com.clt.usecase.RetrieveSplitPerExpenseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class LedgerRouteConfig {
    @Bean
    public RetrieveSplitPerExpenseUseCase retrieveSplitPerExpenseUseCase(
            ExpenseChargeStore expenseChargeStore) {
        return new RetrieveSplitPerExpenseUseCase(expenseChargeStore);
    }

    @Bean
    public RouterFunction<ServerResponse> ledgerRoute(
            RetrieveSplitPerExpenseUseCase retrieveSplitPerExpenseUseCase,
            ExpenseSplitMapper expenseSplitMapper) {
        return new LedgerRouter(retrieveSplitPerExpenseUseCase, expenseSplitMapper).buildRoutes();
    }
}
