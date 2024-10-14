package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.integration;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller.AuthController;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.DuplicatedEmailException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    private static final String USER_NAME = "Test User";

    private static final String USER_EMAIL = "testuser@testuser.com";

    private static final String USER_PASSWORD = "password123";

    @AfterEach
    public void clearRecord() {
        userRepository.deleteAll();
    }

    @Test
    void validRegistrationTest() {
        var registerRequest =
                createRegisterRequest(USER_NAME, USER_EMAIL,
                        USER_PASSWORD, UserRole.USER);

        var response = authController.register(registerRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(registerRequest.getName(), response.getBody().getName())
        );

    }

    @Test
    void notUniqueEmailRegistrationTest() {
        var registerRequest =
                createRegisterRequest(USER_NAME, USER_EMAIL,
                        USER_PASSWORD, UserRole.USER);

        authController.register(registerRequest);

        var registerRequestOther = createRegisterRequest("User Name", USER_EMAIL,
                "pass987", UserRole.USER);


        assertThrows(DuplicatedEmailException.class, () -> authController.register(registerRequestOther));
    }

    @Test
    void validLoginTest() {
        var registerRequest =
                createRegisterRequest(USER_NAME, USER_EMAIL,
                        USER_PASSWORD, UserRole.USER);

        authController.register(registerRequest);

        var loginRequest = createLoginRequest(USER_EMAIL, USER_PASSWORD);

        var response = authController.login(loginRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(StringUtils.isNotBlank(response.getBody().getToken()))
        );

    }

    @Test
    void invalidPasswordLoginTest() {
        var registerRequest =
                createRegisterRequest(USER_NAME, USER_EMAIL,
                        USER_PASSWORD, UserRole.USER);

        authController.register(registerRequest);

        var loginRequest = createLoginRequest(USER_EMAIL, "password1234");

        assertThrows(BadCredentialsException.class, () -> authController.login(loginRequest));

    }

    private RegisterRequest createRegisterRequest(String name, String email, String password, UserRole role) {
        return RegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    private LoginRequest createLoginRequest(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

}
