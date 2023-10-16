package com.clt.expenses.expense;
import com.clt.domain.expense.Money;
import com.clt.expenses.expense.request.CreateExpenseRequestDto;
import com.clt.expenses.expense.response.ExpenseResponse;
import com.clt.usecase.AddExpenseUseCase;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

public class ExpenseRouter {
    private final ExpenseMapper expenseMapper;
    private final AddExpenseUseCase addExpenseUseCase;

    public ExpenseRouter(ExpenseMapper expenseMapper, AddExpenseUseCase addExpenseUseCase) {
        this.expenseMapper = expenseMapper;
        this.addExpenseUseCase = addExpenseUseCase;
    }

    private Mono<ServerResponse> addExpense(ServerRequest serverRequest) {
        String groupId = serverRequest.pathVariable("groupId");
        return serverRequest
                .bodyToMono(CreateExpenseRequestDto.class)
                .map(r -> addExpenseUseCase.create(
                        r.getDescription(),
                        Money.euros(r.getAmount().getAmount()),
                        r.getOwnerId(),
                        groupId
                )).map(expenseMapper::toDto)
                .flatMap(
                        r -> ServerResponse.ok()
                                .body(Mono.just(r), ExpenseResponse.class));
    }

    private Mono<ServerResponse> retrieveExpenses(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    public RouterFunction<ServerResponse> createRoutes() {
        return route()
                .POST(
                        "/group/{groupId}/expense",
                        this::addExpense,
                        ops ->
                                ops.operationId("addExpense")
                                        .tag("Group")
                                        .parameter(
                                                parameterBuilder()
                                                        .in(ParameterIn.PATH)
                                                        .name("groupId")
                                                        .description("Group Id"))
                                        .requestBody(requestBodyBuilder().implementation(CreateExpenseRequestDto.class))
                                        .response(
                                                responseBuilder().responseCode("201").implementation(ExpenseResponse.class))
                                        .response(responseBuilder().responseCode("404").description("Group not found")))
                .GET(
                        "/group/{groupId}/expense",
                        this::retrieveExpenses,
                        ops ->
                                ops.operationId("retrieveExpenses")
                                        .tag("Group")
                                        .parameter(
                                                parameterBuilder()
                                                        .in(ParameterIn.PATH)
                                                        .name("groupId")
                                                        .description("Group Id"))
                                        .response(
                                                responseBuilder()
                                                        .responseCode("200")
                                                        .implementationArray(ExpenseResponse.class))
                                        .response(responseBuilder().responseCode("404").description("Group not found")))
                .build();
    }
}
