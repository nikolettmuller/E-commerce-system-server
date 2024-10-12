package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserResponse {

    @Schema(name = "name", example = "Jane Doe")
    private String name;

    @Schema(name = "email", example = "janedoe@email.com")
    private String email;

    @Schema(name = "role", example = "USER")
    private UserRole role;

    @Schema(name = "createdAt", example = "2024-10-12T14:10:05.591")
    private Timestamp createdAt;
}
