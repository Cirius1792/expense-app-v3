package com.clt.expenses.group;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.GroupFactory;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonStore;
import com.clt.usecase.CreateGroupUseCase;
import com.clt.usecase.FindGroupUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GroupRouterConfig {

  @Bean
  GroupFactory groupFactory() {
    return new GroupFactory(new UUIDIdFactory());
  }

  @Bean
  CreateGroupUseCase createGroupUseCase(
      GroupFactory groupFactory, PersonStore personStore, GroupStore groupStore) {
    return new CreateGroupUseCase(groupFactory, personStore, groupStore);
  }

  @Bean
  FindGroupUseCase findGroupUseCase(PersonStore personStore, GroupStore groupStore) {
    return new FindGroupUseCase(groupStore, personStore);
  }

  @Bean
  RouterFunction<ServerResponse> groupRoutes(
      FindGroupUseCase findGroupUseCase,
      CreateGroupUseCase createGroupUseCase,
      GroupMapper groupMapper) {
    return new GroupRouter(findGroupUseCase, createGroupUseCase, groupMapper).createRoutes();
  }
}
