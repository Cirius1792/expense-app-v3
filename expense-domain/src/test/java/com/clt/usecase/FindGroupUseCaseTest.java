package com.clt.usecase;

import com.clt.domain.group.*;
import com.clt.view.GroupAggregate;
import com.clt.view.GroupAggregateFactory;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FindGroupUseCaseTest {

  private static final String GROUP_ID = "g-id";
  private static final String GROUP_NAME = "My friends";
  private static final Person OWNER = PersonUtil.newPerson();

  private static final Person MEMBER = PersonUtil.newPerson();
  private static final Group GROUP =
      ImmutableGroup.builder()
          .id(GROUP_ID)
          .name(GROUP_NAME)
          .owner(OWNER.id())
          .addMembers(OWNER.id(), MEMBER.id())
          .build();

  private static final GroupAggregate GROUP_AGGREGATE =
      GroupAggregateFactory.fromDomain(GROUP, OWNER, Arrays.asList(OWNER, MEMBER));

  private FindGroupUseCase useCase;

  @BeforeEach
  void initMocks() {
    PersonStore personStore = Mockito.mock(PersonStore.class);
    Mockito.when(personStore.retrieve(Arrays.asList(MEMBER.id(), OWNER.id())))
        .thenReturn(Flux.just(MEMBER, OWNER));

    GroupStore groupStore = Mockito.mock(GroupStore.class);
    Mockito.when(groupStore.retrieve(GROUP_ID))
        .thenReturn(
            Mono.just(
                ImmutableGroup.builder()
                    .id(GROUP_ID)
                    .name(GROUP_NAME)
                    .owner(OWNER.id())
                    .addMembers(MEMBER.id())
                    .build()));
    useCase = new FindGroupUseCase(groupStore, personStore);
  }

  @DisplayName(
      "Given the is of an existing group "
          + "When looking for the group by id "
          + "Then the group is returned")
  @Test
  void retrieve_group_test() {
    useCase
        .retrieve(GROUP_ID)
        .as(StepVerifier::create)
        .expectNext(GROUP_AGGREGATE)
        .verifyComplete();
  }
}
