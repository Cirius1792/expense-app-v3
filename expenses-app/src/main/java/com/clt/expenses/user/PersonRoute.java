package com.clt.expenses.user;

import com.clt.expenses.user.request.CreateUserRequestDto;
import com.clt.expenses.user.response.UserResponseDto;
import com.clt.usecase.FindUserUseCase;
import com.clt.usecase.RegisterPersonUseCase;
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

    private final RegisterPersonUseCase registerPersonUseCase;
    private final FindUserUseCase findUserUseCase;
    private final PersonMapper personMapper;

    public PersonRoute(RegisterPersonUseCase registerPersonUseCase, FindUserUseCase findUserUseCase, PersonMapper personMapper) {
        this.registerPersonUseCase = registerPersonUseCase;
        this.findUserUseCase = findUserUseCase;
        this.personMapper = personMapper;
    }

    private Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CreateUserRequestDto.class)
                .flatMap(r -> registerPersonUseCase.register(r.getUsername()))
                .map(personMapper::toDto)
                .flatMap(r -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(r), UserResponseDto.class));
    }

    private Mono<ServerResponse> retrieveUser(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        return findUserUseCase.retrieve(userId)
                .map(personMapper::toDto)
                .flatMap(r -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(r), UserResponseDto.class));
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
                .GET("/user/{userId}",
                        this::retrieveUser,
                        ops -> ops.operationId("retrieveUser")
                                .tag(USER_TAG)
                                .parameter(parameterBuilder()
                                        .in(ParameterIn.PATH)
                                        .name("userId")
                                        .description("User Unique Identifier"))
                                .response(responseBuilder()
                                        .responseCode("200")
                                        .implementation(UserResponseDto.class))
                                .response(responseBuilder()
                                        .responseCode("404")
                                        .description("User Not Found with the given ID"))
                )
                .build();
    }
}
