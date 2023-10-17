package com.clt.expenses.group;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

import com.clt.expenses.group.request.CreateGroupRequestDto;
import com.clt.expenses.group.response.GroupResponse;
import com.clt.usecase.CreateGroupUseCase;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class GroupRouter {

  private final CreateGroupUseCase createGroupUseCase;
  private final GroupMapper groupMapper;

  public GroupRouter(CreateGroupUseCase createGroupUseCase, GroupMapper groupMapper) {
    this.createGroupUseCase = createGroupUseCase;
    this.groupMapper = groupMapper;
  }

  Mono<ServerResponse> createGroup(ServerRequest request) {
    return request
        .bodyToMono(CreateGroupRequestDto.class)
        .flatMap(r -> createGroupUseCase.create(r.getName(), r.getOwnerId(), r.getMemberIds()))
        .map(groupMapper::toDto)
        .flatMap(r -> ServerResponse.ok().body(Mono.just(r), GroupResponse.class));
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
        .build();
  }
}
