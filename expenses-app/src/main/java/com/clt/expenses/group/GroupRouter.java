package com.clt.expenses.group;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

import com.clt.expenses.group.request.CreateGroupRequestDto;
import com.clt.expenses.group.response.GroupResponse;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.fn.builders.parameter.Builder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class GroupRouter {

  private final FindGroupUseCase findGroupUseCase;
  private final CreateGroupUseCase createGroupUseCase;
  private final GroupMapper groupMapper;

  public GroupRouter(
      FindGroupUseCase findGroupUseCase,
      CreateGroupUseCase createGroupUseCase,
      GroupMapper groupMapper) {
    this.findGroupUseCase = findGroupUseCase;
    this.createGroupUseCase = createGroupUseCase;
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
        .build();
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
