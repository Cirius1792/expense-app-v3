package com.clt.expenses.user;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

import com.clt.expenses.user.request.CreateUserRequestDto;
import com.clt.expenses.user.response.UserResponseDto;
import com.clt.usecase.RegisterPersonUseCase;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class PersonRoute {
  private static final String USER_TAG = "Users";

  private final RegisterPersonUseCase registerPersonUseCase;
  private final PersonMapper personMapper;

  public PersonRoute(RegisterPersonUseCase registerPersonUseCase, PersonMapper personMapper) {
    this.registerPersonUseCase = registerPersonUseCase;
    this.personMapper = personMapper;
  }

  private Mono<ServerResponse> createUser(ServerRequest serverRequest) {
    return serverRequest
        .bodyToMono(CreateUserRequestDto.class)
        .flatMap(r -> registerPersonUseCase.register(r.getUsername()))
        .map(personMapper::toDto)
        .flatMap(r -> ServerResponse.ok().body(Mono.just(r), UserResponseDto.class));
  }

  public RouterFunction<ServerResponse> createRoutes() {
    return route()
        .POST(
            "/user",
            this::createUser,
            ops ->
                ops.operationId("createUser")
                    .tag(USER_TAG)
                    .requestBody(requestBodyBuilder().implementation(CreateUserRequestDto.class))
                    .response(
                        responseBuilder().responseCode("201").implementation(UserResponseDto.class))
                    .response(responseBuilder().responseCode("400").description("Invalid Request")))
        .build();
  }
}
