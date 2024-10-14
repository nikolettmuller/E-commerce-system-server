package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.UserMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.UserMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    private static final String USER_NAME = "Test User";

    private static final String USER_EMAIL = "testuser@testuser.com";

    private static final String USER_PASSWORD = "password123";

    @Test
    void userMapperTest() {
        var user = createUser(1, USER_NAME, UserRole.USER, USER_EMAIL, USER_PASSWORD);

        var mappedUserResponse = userMapper.map(user);

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getName(), mappedUserResponse.getName()),
                () -> Assertions.assertEquals(user.getRole(), mappedUserResponse.getRole()),
                () -> Assertions.assertEquals(user.getEmail(), mappedUserResponse.getEmail()),
                () -> Assertions.assertEquals(user.getCreatedAt(), mappedUserResponse.getCreatedAt())
        );
    }

    private User createUser(Integer id, String name, UserRole role, String email, String password) {
        var user = new User();
        user.setId(id);
        user.setName(name);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }
}
