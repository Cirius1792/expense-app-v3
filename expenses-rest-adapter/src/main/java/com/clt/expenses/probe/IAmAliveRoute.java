package com.clt.expenses.probe;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

public class IAmAliveRoute {
  public RouterFunction<ServerResponse> createRoutes() {
    return route()
        .GET(
            "/",
            this::iAmAlive,
            ops ->
                ops.operationId("iAmAliveProbe")
                    .tag("Management")
                    .response(responseBuilder().responseCode("200").implementation(String.class)))
        .build();
  }

  private Mono<ServerResponse> iAmAlive(ServerRequest serverRequest) {
    return ServerResponse.ok().body(Mono.just("I Am Alive"), String.class);
  }
}
