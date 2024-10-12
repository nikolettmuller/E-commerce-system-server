package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.integration;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller.AuthController;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.DuplicatedEmailException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void clearRecord() {
        userRepository.deleteAll();
    }

    @Test
    void validRegistrationTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        ResponseEntity<RegisterResponse> response = authController.register(registerRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(registerRequest.getName(), response.getBody().getName())
        );

    }

    @Test
    void notUniqueEmailRegistrationTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        authController.register(registerRequest);

        RegisterRequest registerRequestOther = RegisterRequest.builder()
                .name("Test Other")
                .email("testuser@testuser.com")
                .password("password1234")
                .role(UserRole.USER)
                .build();

        assertThrows(DuplicatedEmailException.class, () -> authController.register(registerRequestOther));
    }

    @Test
    void invalidEmailRegistrationTest() {
       // TODO
    }

    @Test
    void nullAttributesRegistrationTest() {
        // TODO
    }

    @Test
    void validLoginTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        authController.register(registerRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email(registerRequest.getEmail())
                .password("password123")
                .build();

        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(StringUtils.isNotBlank(response.getBody().getToken()))
        );

    }

    @Test
    void invalidPasswordLoginTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        authController.register(registerRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email(registerRequest.getEmail())
                .password("password1234")
                .build();

        assertThrows(BadCredentialsException.class, () -> authController.login(loginRequest));

    }

}
