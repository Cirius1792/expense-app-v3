package com.clt.expenses.probe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ManagementRouteConfiguration {
  @Bean
  RouterFunction<ServerResponse> iAmAliveRoute() {
    return new IAmAliveRoute().createRoutes();
  }
}
