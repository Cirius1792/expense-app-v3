package com.clt.expenses.security;

import com.clt.domain.registry.InvalidUsernameError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.test.StepVerifier;

class ApplicationCredentialManagerImplTest {

    public static final String USERNAME = "mario.rossi";
    public static final String MASKED_PASSWORD = "masked_password";
    public static final String NEW_USERNAME = "super.mario.rossi";
    private static final ApplicationUser APPLICATION_USER =
            new ApplicationUser(USERNAME, MASKED_PASSWORD);

    public static final String PLAIN_PASSWORD = "password";
    private static final ApplicationUser NEW_APPLICATION_USER =
            new ApplicationUser(NEW_USERNAME, MASKED_PASSWORD);
    private ApplicationCredentialManager credentialManager;

    @BeforeEach
    void initMocks(){
        UserDetailsStore userDetailsStore = Mockito.mock(UserDetailsStore.class);
        Mockito.when(userDetailsStore.findByUsername(USERNAME)).thenReturn(Mono.just(APPLICATION_USER));
        Mockito.when(userDetailsStore.findByUsername(NEW_USERNAME)).thenReturn(Mono.empty());
        Mockito.when(userDetailsStore.store(Mockito.any())).thenReturn(Mono.create(MonoSink::success));
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(encoder.encode(PLAIN_PASSWORD)).thenReturn(MASKED_PASSWORD);
        this.credentialManager = new ApplicationCredentialManagerImpl(userDetailsStore, encoder);
    }

    @DisplayName(
            """
                 Given a registered user
                 When retrieving the user by username
                 Then the application user is returned
                 """)
    @Test
    void retrieveUserByUsername() {
        credentialManager
                .retrieve(USERNAME)
                .as(StepVerifier::create)
                .expectNext(APPLICATION_USER)
                .verifyComplete();
    }

    @DisplayName(
            """
                    Given a new user
                    When the user is registered
                    Then its credentials are stored
                    And the password is masked
                    """)
    @Test
    void registerUser() {
        credentialManager
                .register(NEW_USERNAME, PLAIN_PASSWORD)
                .as(StepVerifier::create)
                .expectNext(NEW_APPLICATION_USER)
                .verifyComplete();
    }

    @DisplayName(
            """
                    Given a new user
                    When the user tries to register with a non unique username
                    Then an exception is thrown
                    """)
    @Test
    void registerUserShouldFailBecauseOfNonUniqueUsername() {
        credentialManager
                .register(USERNAME, PLAIN_PASSWORD)
                .as(StepVerifier::create)
                .expectError(InvalidUsernameError.class)
                .verify();
    }
}
