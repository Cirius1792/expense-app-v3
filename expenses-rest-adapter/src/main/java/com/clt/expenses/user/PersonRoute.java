package com.clt.expenses.user;

import com.clt.expenses.user.request.CreateUserRequestDto;
import com.clt.expenses.user.response.UserGroupDto;
import com.clt.expenses.user.response.UserResponseDto;
import com.clt.usecase.FindUserUseCase;
import com.clt.usecase.RegisterPersonUseCase;
import com.clt.usecase.RetrieveGroupPerUserUseCase;
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

public class PersonRoute {
  private static final String USER_TAG = "Users";
  public static final String USER_ID_PARAM = "userId";

  private final RegisterPersonUseCase registerPersonUseCase;
  private final FindUserUseCase findUserUseCase;
  private final RetrieveGroupPerUserUseCase retrieveGroupPerUserUseCase;
  private final PersonMapper personMapper;
  private final PersonGroupMapper personGroupMapper;

  public PersonRoute(
      RegisterPersonUseCase registerPersonUseCase,
      FindUserUseCase findUserUseCase,
      RetrieveGroupPerUserUseCase retrieveGroupPerUserUseCase,
      PersonMapper personMapper,
      PersonGroupMapper personGroupMapper) {
    this.registerPersonUseCase = registerPersonUseCase;
    this.findUserUseCase = findUserUseCase;
    this.retrieveGroupPerUserUseCase = retrieveGroupPerUserUseCase;
    this.personMapper = personMapper;
    this.personGroupMapper = personGroupMapper;
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
        .GET(
            "/user/{userId}",
            this::retrieveUser,
            ops ->
                ops.operationId("retrieveUser")
                    .tag(USER_TAG)
                    .parameter(
                        parameterBuilder()
                            .in(ParameterIn.PATH)
                            .name(USER_ID_PARAM)
                            .description("User Unique Identifier"))
                    .response(
                        responseBuilder().responseCode("200").implementation(UserResponseDto.class))
                    .response(
                        responseBuilder()
                            .responseCode("404")
                            .description("User Not Found with the given ID")))
        .GET(
            "/user/{userId}/groups",
            this::retrieveUserGroups,
            ops ->
                ops.operationId("retrieveUserGroups")
                    .tag(USER_TAG)
                    .parameter(
                        parameterBuilder()
                            .in(ParameterIn.PATH)
                            .name(USER_ID_PARAM)
                            .description("User Unique Identifier"))
                    .response(
                        responseBuilder()
                            .responseCode("200")
                            .implementationArray(UserGroupDto.class))
                    .response(
                        responseBuilder()
                            .responseCode("404")
                            .description("User Not Found with the given ID")))
        .build();
  }

  private Mono<ServerResponse> retrieveUserGroups(ServerRequest serverRequest) {
    String userId = serverRequest.pathVariable(USER_ID_PARAM);
    return ServerResponse.ok()
        .body(
            retrieveGroupPerUserUseCase.retrieveGroups(userId).map(personGroupMapper::toDto),
            UserGroupDto.class);
  }

  private Mono<ServerResponse> createUser(ServerRequest serverRequest) {
    return serverRequest
        .bodyToMono(CreateUserRequestDto.class)
        .flatMap(r -> registerPersonUseCase.register(r.getUsername()))
        .map(personMapper::toDto)
        .flatMap(
            r ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(r), UserResponseDto.class));
  }

  private Mono<ServerResponse> retrieveUser(ServerRequest serverRequest) {
    String userId = serverRequest.pathVariable(USER_ID_PARAM);
    return findUserUseCase
        .retrieve(userId)
        .map(personMapper::toDto)
        .flatMap(
            r ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(r), UserResponseDto.class));
  }
}
