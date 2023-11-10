package com.clt.expenses.ledger;

import com.clt.expenses.ledger.response.ExpenseSplitDto;
import com.clt.usecase.RetrieveSplitPerExpenseUseCase;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.fn.builders.parameter.Builder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

public class LedgerRouter {
  public static final String EXPENSES_TAG = "Expenses";

  private final RetrieveSplitPerExpenseUseCase retrieveSplitPerExpenseUseCase;
  private final ExpenseSplitMapper expenseSplitMapper;

  public LedgerRouter(
      RetrieveSplitPerExpenseUseCase retrieveSplitPerExpenseUseCase,
      ExpenseSplitMapper expenseSplitMapper) {
    this.expenseSplitMapper = expenseSplitMapper;
    this.retrieveSplitPerExpenseUseCase = retrieveSplitPerExpenseUseCase;
  }

  public RouterFunction<ServerResponse> buildRoutes() {
    return route()
        .GET(
            "/expense/{expenseId}/split",
            this::retrieveSplitPerExpense,
            ops ->
                ops.operationId("retrieveSplitPerExpense")
                    .tag(EXPENSES_TAG)
                    .parameter(
                        Builder.parameterBuilder()
                            .in(ParameterIn.PATH)
                            .name("expenseId")
                            .description(
                                "Identifier of the expense for which the split was generated"))
                    .response(
                        responseBuilder()
                            .responseCode("200")
                            .implementationArray(ExpenseSplitDto.class)))
        .build();
  }

  public Mono<ServerResponse> retrieveSplitPerExpense(ServerRequest request) {
    String expenseId = request.pathVariable("expenseId");
    return ServerResponse.ok()
        .body(
            retrieveSplitPerExpenseUseCase.retrieve(expenseId).map(expenseSplitMapper::toDto),
            ExpenseSplitDto.class);
  }
}
