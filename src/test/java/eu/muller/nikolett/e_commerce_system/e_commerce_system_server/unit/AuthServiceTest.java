package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.DuplicatedEmailException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RegisterMapper registerMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    private static final String USER_NAME = "Test User";

    private static final String USER_EMAIL = "testuser@testuser.com";

    private static final String USER_PASSWORD = "password123";

    @Test
    void validRegisterTest() {
        RegisterRequest registerRequest =
                createRegisterRequest(USER_NAME, USER_EMAIL,
                        USER_PASSWORD, UserRole.USER);

        User mappedUser = createMappedUser(1, registerRequest.getName(), registerRequest.getRole(), registerRequest.getEmail());

        doReturn(0L).when(userRepository).countByEmail(registerRequest.getEmail());
        doReturn("encryptedPass").when(passwordEncoder).encode(registerRequest.getPassword());
        doReturn(mappedUser).when(registerMapper).map(registerRequest);
        doReturn(mappedUser).when(userRepository).save(mappedUser);

        RegisterResponse registerResponse = authService.register(registerRequest);

        Assertions.assertEquals(USER_NAME, registerResponse.getName());
    }

    @Test
    void duplicatedEmailTest() {
        RegisterRequest registerRequest =
                createRegisterRequest(USER_NAME, USER_EMAIL,
                        USER_PASSWORD, UserRole.USER);

        doReturn(1L).when(userRepository).countByEmail(registerRequest.getEmail());

        Assertions.assertThrows(DuplicatedEmailException.class, () -> authService.register(registerRequest));
    }

    private RegisterRequest createRegisterRequest(String name, String email, String password, UserRole role) {
        return RegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    private User createMappedUser(Integer id, String name, UserRole role, String email) {
        User mappedUser = new User();
        mappedUser.setId(id);
        mappedUser.setName(name);
        mappedUser.setRole(role);
        mappedUser.setEmail(email);
        return mappedUser;
    }
}
