package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.integration;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller.UserController;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.UserNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    private static final String USER_NAME = "Test User";

    private static final String USER_EMAIL = "testuser@testuser.com";

    private static final String USER_PASSWORD = "password123";

    @BeforeEach
    public void initRecord() {
        userRepository.save(createUser(USER_NAME, UserRole.USER, USER_EMAIL, USER_PASSWORD));
    }

    @AfterEach
    public void clearRecord() {
        userRepository.deleteAll();
    }


    @Test
    void validUserInfoTest() {
        int userId = userRepository.findAll().get(0).getId();
        var response = userController.findUser(userId);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    void invalidUserInfoTest() {
        var userId = 20;
        assertThrows(UserNotFoundException.class, () -> userController.findUser(userId));
    }


    private User createUser(String name, UserRole role, String email, String password) {
        var user = new User();
        user.setName(name);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

}
