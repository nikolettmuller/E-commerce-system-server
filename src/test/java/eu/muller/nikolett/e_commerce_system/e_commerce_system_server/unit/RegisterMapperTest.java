package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RegisterMapperTest {

    private final RegisterMapper registerMapper = new RegisterMapperImpl();

    @Test
    void registerMapperTest() {
        var registerRequest = RegisterRequest.builder()
                .name("Test User")
                .email("testuser@testuser.com")
                .password("password123")
                .role(UserRole.USER)
                .build();

        var mappedUser = registerMapper.map(registerRequest);

        Assertions.assertAll(
                () -> Assertions.assertEquals(registerRequest.getName(), mappedUser.getName()),
                () -> Assertions.assertEquals(registerRequest.getRole(), mappedUser.getRole()),
                () -> Assertions.assertEquals(registerRequest.getEmail(), mappedUser.getEmail()),
                () -> Assertions.assertEquals(registerRequest.getPassword(), mappedUser.getPassword()),
                () -> Assertions.assertNotNull(mappedUser.getCreatedAt())
        );
    }

}
