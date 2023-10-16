package com.clt.expenses.group;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.GroupFactory;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonStore;
import com.clt.usecase.CreateGroupUseCase;
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
    CreateGroupUseCase createGroupUseCase(GroupFactory groupFactory,
                                          PersonStore personStore,
                                          GroupStore groupStore) {
        return new CreateGroupUseCase(groupFactory, personStore, groupStore);
    }

    @Bean
    RouterFunction<ServerResponse> groupRoutes(
            CreateGroupUseCase createGroupUseCase,
            GroupMapper groupMapper) {
        return new GroupRouter(createGroupUseCase, groupMapper)
                .createRoutes();
    }
}
