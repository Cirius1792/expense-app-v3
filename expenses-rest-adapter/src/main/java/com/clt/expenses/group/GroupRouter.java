package com.clt.expenses.group;

import com.clt.domain.expense.Money;
import com.clt.expenses.expense.request.MoneyDto;
import com.clt.expenses.group.request.CreateGroupRequestDto;
import com.clt.expenses.group.request.PaymentRequest;
import com.clt.expenses.group.response.BalanceResponse;
import com.clt.expenses.group.response.GroupResponse;
import com.clt.expenses.group.response.PaymentResponse;
import com.clt.usecase.CreateGroupWithMembersUseCase;
import com.clt.usecase.FindGroupUseCase;
import com.clt.usecase.PayUseCase;
import com.clt.usecase.RetrieveUserBalancePerGroupUseCase;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.fn.builders.parameter.Builder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

public class GroupRouter {

  private final FindGroupUseCase findGroupUseCase;
  private final CreateGroupWithMembersUseCase createGroupUseCase;
  private final RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase;
  private final PayUseCase payUseCase;
  private final GroupMapper groupMapper;

  public GroupRouter(
      FindGroupUseCase findGroupUseCase,
      CreateGroupWithMembersUseCase createGroupUseCase,
      RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase,
      PayUseCase payUseCase,
      GroupMapper groupMapper) {
    this.findGroupUseCase = findGroupUseCase;
    this.createGroupUseCase = createGroupUseCase;
    this.retrieveUserBalancePerGroupUseCase = retrieveUserBalancePerGroupUseCase;
    this.payUseCase = payUseCase;
    this.groupMapper = groupMapper;
  }

  public RouterFunction<ServerResponse> createRoutes() {
    return route()
        .POST(
            "/group",
            this::createGroup,
            ops ->
                ops.operationId("createGroup")
                    .tag("Group")
                    .requestBody(requestBodyBuilder().implementation(CreateGroupRequestDto.class))
                    .response(
                        responseBuilder().responseCode("201").implementation(GroupResponse.class))
                    .response(
                        responseBuilder()
                            .responseCode("400")
                            .description("Missing Required parameters")))
        .GET(
            "/group/{groupId}",
            this::retrieveGroup,
            ops ->
                ops.operationId("retrieveGroupById")
                    .tag("Group")
                    .parameter(
                        Builder.parameterBuilder()
                            .in(ParameterIn.PATH)
                            .description("Group unique Id")
                            .name("groupId"))
                    .response(
                        responseBuilder().responseCode("200").implementation(GroupResponse.class))
                    .response(
                        responseBuilder()
                            .responseCode("404")
                            .description("No group found for the given id")))
        .GET(
            "/group/{groupId}/user/{userId}/balance",
            this::retrieveUserBalance,
            ops ->
                ops.operationId("retrieveBalancePerGroupPerUser")
                    .tag("Group")
                    .parameter(
                        Builder.parameterBuilder()
                            .in(ParameterIn.PATH)
                            .description("Group unique Id")
                            .name("groupId"))
                    .parameter(
                        Builder.parameterBuilder()
                            .in(ParameterIn.PATH)
                            .description("Group unique Id")
                            .name("userId"))
                    .response(
                        responseBuilder().responseCode("200").implementation(BalanceResponse.class))
                    .response(
                        responseBuilder()
                            .responseCode("404")
                            .description("No group found for the given id")))
        .POST(
            "/group/{groupId}/user/{userId}/pay",
            this::payUser,
            ops ->
                ops.operationId("payUser")
                    .tag("Group")
                    .parameter(
                        Builder.parameterBuilder()
                            .in(ParameterIn.PATH)
                            .description("Group unique Id")
                            .name("groupId"))
                    .parameter(
                        Builder.parameterBuilder()
                            .in(ParameterIn.PATH)
                            .description("Group unique Id")
                            .name("userId"))
                    .requestBody(requestBodyBuilder().implementation(PaymentRequest.class))
                    .response(
                        responseBuilder()
                            .responseCode("200")
                            .implementation(PaymentResponse.class)))
        .build();
  }

  private Mono<ServerResponse> payUser(ServerRequest serverRequest) {
    String groupId = serverRequest.pathVariable("groupId");
    String payerUser = serverRequest.pathVariable("userId");
    return serverRequest
        .bodyToMono(PaymentRequest.class)
        .flatMap(
            r ->
                this.payUseCase.pay(
                    groupId,
                    payerUser,
                    r.getPayedUserId(),
                    Money.euros(r.getAmountToPay().getAmount())))
        .map(r -> new PaymentResponse(r.getId(), new MoneyDto(r.getAmount().getAmount(), MoneyDto.CurrencyEnum.EUR)))
        .transform(r -> ServerResponse.ok().body(r, PaymentResponse.class));
  }

  private Mono<ServerResponse> retrieveUserBalance(ServerRequest serverRequest) {
    String groupId = serverRequest.pathVariable("groupId");
    String userId = serverRequest.pathVariable("userId");
    return retrieveUserBalancePerGroupUseCase
        .retrieve(userId, groupId)
        .map(b -> new BalanceResponse(new MoneyDto(b.getAmount(), MoneyDto.CurrencyEnum.EUR)))
        .flatMap(r -> ServerResponse.ok().body(Mono.just(r), BalanceResponse.class));
  }

  Mono<ServerResponse> createGroup(ServerRequest request) {
    return request
        .bodyToMono(CreateGroupRequestDto.class)
        .flatMap(r -> createGroupUseCase.create(r.getName(), r.getOwnerId(), r.getMemberIds()))
        .map(groupMapper::toDto)
        .flatMap(r -> ServerResponse.ok().body(Mono.just(r), GroupResponse.class));
  }

  private Mono<ServerResponse> retrieveGroup(ServerRequest serverRequest) {
    String groupId = serverRequest.pathVariable("groupId");
    return ServerResponse.ok()
        .body(findGroupUseCase.retrieve(groupId).map(groupMapper::toDto), GroupResponse.class);
  }
}
