package com.clt.expenses.ledger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.clt.usecase.RetrieveSplitPerExpenseUseCase;

@Configuration
public class LedgerRouteConfig {

    @Bean
    public RouterFunction<ServerResponse> ledgerRoute(
            RetrieveSplitPerExpenseUseCase retrieveSplitPerExpenseUseCase,
            ExpenseSplitMapper expenseSplitMapper) {
        return new LedgerRouter(retrieveSplitPerExpenseUseCase, expenseSplitMapper).buildRoutes();
    }
}
