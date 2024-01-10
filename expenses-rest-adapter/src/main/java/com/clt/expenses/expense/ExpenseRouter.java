package com.clt.expenses.expense;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

import com.clt.domain.common.error.ResourceNotFoundError;
import com.clt.domain.expense.Money;
import com.clt.expenses.expense.request.CreateExpenseRequestDto;
import com.clt.expenses.expense.response.ExpenseResponse;
import com.clt.expenses.expense.response.ResourceNotFoundResponse;
import com.clt.usecase.AddExpenseUseCase;
import com.clt.usecase.FindExpenseUseCase;
import com.clt.usecase.FindExpensesPerGroupUseCase;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ExpenseRouter {
  private static final Logger logger = LoggerFactory.getLogger(ExpenseRouter.class);

  private static final String EXPENSE_TAG = "Expenses";
  private static final String GROUP_ID_PARAMETER = "groupId";
  private final ExpenseMapper expenseMapper;
  private final AddExpenseUseCase addExpenseUseCase;
  private final FindExpenseUseCase findExpenseUseCase;
  private final FindExpensesPerGroupUseCase findExpensesPerGroupUseCase;

  public ExpenseRouter(ExpenseMapper expenseMapper, AddExpenseUseCase addExpenseUseCase,
      FindExpenseUseCase findExpenseUseCase,
      FindExpensesPerGroupUseCase findExpensesPerGroupUseCase) {
    this.expenseMapper = expenseMapper;
    this.addExpenseUseCase = addExpenseUseCase;
    this.findExpenseUseCase = findExpenseUseCase;
    this.findExpensesPerGroupUseCase = findExpensesPerGroupUseCase;
  }

  public RouterFunction<ServerResponse> createRoutes() {
    return route()
        .POST("/group/{groupId}/expense", this::addExpense,
            ops
            -> ops.operationId("addExpense")
                   .tag(EXPENSE_TAG)
                   .parameter(parameterBuilder()
                                  .in(ParameterIn.PATH)
                                  .name(GROUP_ID_PARAMETER)
                                  .description("Group Id"))
                   .requestBody(requestBodyBuilder().implementation(CreateExpenseRequestDto.class))
                   .response(
                       responseBuilder().responseCode("201").implementation(ExpenseResponse.class))
                   .response(responseBuilder().responseCode("404").description("Group not found")))
        .GET("/group/{groupId}/expense", this::retrieveExpenses,
            ops
            -> ops.operationId("retrieveExpenses")
                   .tag(EXPENSE_TAG)
                   .parameter(parameterBuilder()
                                  .in(ParameterIn.PATH)
                                  .name(GROUP_ID_PARAMETER)
                                  .description("Group Unique Identifier"))
                   .response(responseBuilder().responseCode("200").implementationArray(
                       ExpenseResponse.class))
                   .response(responseBuilder().responseCode("404").description("Group not found")))
        .GET("/expense/{expenseId}", this::retrieveExpense,
            ops
            -> ops.operationId("retrieveExpense")
                   .tag(EXPENSE_TAG)
                   .parameter(parameterBuilder()
                                  .in(ParameterIn.PATH)
                                  .name("expenseId")
                                  .description("Expense Unique Identifier"))
                   .response(
                       responseBuilder().responseCode("200").implementation(ExpenseResponse.class))
                   .response(responseBuilder().responseCode("404").description("Group not found")))
        .build();
  }

  private Mono<ServerResponse> addExpense(ServerRequest serverRequest) {
    String groupId = serverRequest.pathVariable(GROUP_ID_PARAMETER);
    return serverRequest.bodyToMono(CreateExpenseRequestDto.class)
        .flatMap(r
            -> addExpenseUseCase
                   .create(r.getDescription(), Money.euros(r.getAmount().getAmount()),
                       r.getOwnerId(), groupId)
                   .map(expenseMapper::toDto))
        .flatMap(r
            -> ServerResponse.created(URI.create("/expense/" + r.getId()))
                   .body(Mono.just(r), ExpenseResponse.class));
  }

  private Mono<ServerResponse> retrieveExpense(ServerRequest serverRequest) {
    String expenseId = serverRequest.pathVariable("expenseId");
    logger.info("Find Expense By Id [{}]", expenseId);
    return findExpenseUseCase.retrieveExpense(expenseId)
        .map(expenseMapper::toDto)
        .flatMap(r -> ServerResponse.ok().body(Mono.just(r), ExpenseResponse.class))
        .onErrorResume(ResourceNotFoundError.class,
            e
            -> ServerResponse.status(404).body(
                Mono.just(ResourceNotFoundResponse.create(expenseId, e.getMessage())),
                ResourceNotFoundResponse.class));
  }

  private Mono<ServerResponse> retrieveExpenses(ServerRequest serverRequest) {
    String groupId = serverRequest.pathVariable(GROUP_ID_PARAMETER);
    return ServerResponse.ok().body(
        this.findExpensesPerGroupUseCase.retrieve(groupId, 1, 10).map(expenseMapper::toDto),
        ExpenseResponse.class);
  }
}
