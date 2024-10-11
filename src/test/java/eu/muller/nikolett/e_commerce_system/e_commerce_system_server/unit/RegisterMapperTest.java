package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

class RegisterMapperTest {

    private final RegisterMapper registerMapper = new RegisterMapperImpl();

    @Test
    void registerMapperTest() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        Timestamp timestampBeforeMapping = Timestamp.valueOf(LocalDateTime.now());
        User mappedUser = registerMapper.map(registerRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(registerRequest.getName(), mappedUser.getName()),
                () -> Assertions.assertEquals(registerRequest.getRole(), mappedUser.getRole()),
                () -> Assertions.assertEquals(registerRequest.getEmail(), mappedUser.getEmail()),
                () -> Assertions.assertEquals(registerRequest.getPassword(), mappedUser.getPassword()),
                () -> Assertions.assertTrue(timestampBeforeMapping.before(mappedUser.getCreatedAt()))
        );
    }

}
