package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.UserResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.DuplicatedEmailException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.UserNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.UserMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.UserService;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private static final String USER_NAME = "Test User";

    private static final String USER_EMAIL = "testuser@testuser.com";

    private static final String USER_PASSWORD = "password123";

    @Test
    void validUserInfoTest() {
        Integer userId = 1;
        Optional<User> user = Optional.of(createUser(userId, USER_NAME, UserRole.USER, USER_EMAIL, USER_PASSWORD));
        UserResponse userResponse = createUserResponse(USER_NAME, USER_EMAIL, UserRole.USER, user.get().getCreatedAt());

        doReturn(user).when(userRepository).findById(userId);
        doReturn(userResponse).when(userMapper).map(user.get());

        UserResponse response = userService.findUserById(userId);

        Assertions.assertEquals(USER_NAME, response.getName());
    }

    @Test
    void invalidUserInfoTest() {
        Integer userId = 1;
        doReturn(Optional.empty()).when(userRepository).findById(userId);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));

    }

    private User createUser(Integer id, String name, UserRole role, String email, String password) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    private UserResponse createUserResponse(String name, String email, UserRole role, Timestamp createdAt) {
        UserResponse user = UserResponse.builder()
                .name(name)
                .email(email)
                .role(role)
                .createdAt(createdAt)
                .build();
        return user;
    }
}
