package com.clt.expenses.user;

import com.clt.expenses.security.ApplicationCredentialManager;
import com.clt.usecase.FindUserUseCase;
import com.clt.usecase.RegisterUserUseCase;
import com.clt.usecase.RetrieveGroupPerUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PersonRouterConfig {


  @Bean
  RouterFunction<ServerResponse> userRouter(
      FindUserUseCase findUserUseCase,
      RegisterUserUseCase createUserUseCase,
      RetrieveGroupPerUserUseCase retrieveGroupPerUserUseCase,
      PersonMapper personMapper,
      PersonGroupMapper personGroupMapper,
      ApplicationCredentialManager applicationCredentialManager) {
    return new PersonRoute(
            createUserUseCase,
            findUserUseCase,
            retrieveGroupPerUserUseCase,
            personMapper,
            personGroupMapper,
            applicationCredentialManager)
        .createRoutes();
  }
}
