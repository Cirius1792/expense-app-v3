package com.clt.expenses.group;

import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
import com.clt.usecase.RetrieveUserBalancePerGroupUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GroupRouterConfig {


  @Bean
  RouterFunction<ServerResponse> groupRoutes(
      FindGroupUseCase findGroupUseCase,
      CreateGroupUseCase createGroupUseCase,
      GroupMapper groupMapper,
      RetrieveUserBalancePerGroupUseCase retrieveUserBalancePerGroupUseCase) {
    return new GroupRouter(
            findGroupUseCase, createGroupUseCase, retrieveUserBalancePerGroupUseCase, groupMapper)
        .createRoutes();
  }
}
