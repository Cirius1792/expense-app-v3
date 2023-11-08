package com.clt.expenses.group;

import com.clt.expenses.expense.request.MoneyDto;
import com.clt.expenses.group.request.CreateGroupRequestDto;
import com.clt.expenses.group.response.BalanceResponse;
import com.clt.expenses.group.response.GroupResponse;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
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
  private final CreateGroupUseCase createGroupUseCase;
  private final RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase;
  private final GroupMapper groupMapper;

  public GroupRouter(
      FindGroupUseCase findGroupUseCase,
      CreateGroupUseCase createGroupUseCase,
      RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase,
      GroupMapper groupMapper) {
    this.findGroupUseCase = findGroupUseCase;
    this.createGroupUseCase = createGroupUseCase;
    this.retrieveUserBalancePerGroupUseCase = retrieveUserBalancePerGroupUseCase;
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
        .build();
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
