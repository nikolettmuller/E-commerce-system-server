package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.DuplicatedEmailException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.AuthServiceImpl;
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

    @Test
    void validRegisterTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        User mappedUser = new User();
        mappedUser.setId(1);
        mappedUser.setName(registerRequest.getName());
        mappedUser.setRole(registerRequest.getRole());
        mappedUser.setEmail(registerRequest.getEmail());

        doReturn(0L).when(userRepository).countByEmail(registerRequest.getEmail());
        doReturn("encryptedPass").when(passwordEncoder).encode(registerRequest.getPassword());
        doReturn(mappedUser).when(registerMapper).map(registerRequest);
        doReturn(mappedUser).when(userRepository).save(mappedUser);

        RegisterResponse registerResponse = authService.register(registerRequest);

        Assertions.assertEquals("Test User", registerResponse.getName());
    }

    @Test
    void duplicatedEmailTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        doReturn(1L).when(userRepository).countByEmail(registerRequest.getEmail());

        Assertions.assertThrows(DuplicatedEmailException.class, () -> authService.register(registerRequest));
    }
}
