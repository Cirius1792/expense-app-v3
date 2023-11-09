package com.clt.usecase;

import com.clt.domain.group.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class RetrieveGroupPerUserUseCaseTest {

  private static final Person USER = PersonUtil.newPerson();
  private static final List<Group> GROUPS =
      Arrays.asList(
          GroupUtil.newGroup("Home", Collections.singletonList(USER)),
          GroupUtil.newGroup("Friends", Collections.singletonList(USER)),
          GroupUtil.newGroup("Family", Collections.singletonList(USER)));
  private static RetrieveGroupPerUserUseCase useCase;

  @BeforeAll
  static void initMocks() {
    GroupStore groupStore = Mockito.mock(GroupStore.class);
    Mockito.when(groupStore.retrieveByMember(USER.id())).thenReturn(Flux.fromIterable(GROUPS));
    useCase = new RetrieveGroupPerUserUseCase(groupStore);
  }

  @DisplayName(
      "Given a user who is member of the groups: Home, Friends and Family "
          + "When the user looks for the groups he belongs to "
          + "Then the groups Home, Friends and Family are returned")
  @Test
  void user_retrieves_groups_he_belongs_to_test() {
    useCase
        .retrieveGroups(USER.id())
        .as(StepVerifier::create)
        .expectNextSequence(GROUPS)
        .verifyComplete();
  }
}
