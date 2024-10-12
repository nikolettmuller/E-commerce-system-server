package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
@Builder
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    @Schema(name = "name", example = "Jane Doe")
    private String name;

    @Email
    @NotBlank(message = "Email is required")
    @Schema(name = "email", example = "janedoe@email.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(name = "password", example = "Password123")
    private String password;

    @NotNull(message = "Role is required")
    @Schema(name = "role", example = "USER")
    private UserRole role;
}
