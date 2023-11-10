package com.clt.expenses.user;

import com.clt.domain.commons.UUIDIdFactory;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.PersonFactory;
import com.clt.domain.group.PersonStore;
import com.clt.usecase.FindUserUseCase;
import com.clt.usecase.RegisterPersonUseCase;
import com.clt.usecase.RetrieveGroupPerUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PersonRouterConfig {

  @Bean
  RegisterPersonUseCase registerPersonUseCase(PersonStore personStore) {
    return new RegisterPersonUseCase(new PersonFactory(new UUIDIdFactory()), personStore);
  }

  @Bean
  FindUserUseCase findUserUseCase(PersonStore personStore) {
    return new FindUserUseCase(personStore);
  }

  @Bean
  RetrieveGroupPerUserUseCase retrieveGroupPerUserUseCase(GroupStore groupStore) {
    return new RetrieveGroupPerUserUseCase(groupStore);
  }

  @Bean
  RouterFunction<ServerResponse> userRouter(
      FindUserUseCase findUserUseCase,
      RegisterPersonUseCase createUserUseCase,
      RetrieveGroupPerUserUseCase retrieveGroupPerUserUseCase,
      PersonMapper personMapper,
      PersonGroupMapper personGroupMapper) {
    return new PersonRoute(
            createUserUseCase,
            findUserUseCase,
            retrieveGroupPerUserUseCase,
            personMapper,
            personGroupMapper)
        .createRoutes();
  }
}