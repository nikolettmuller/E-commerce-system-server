package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @Email
    @NotBlank(message = "Email is required")
    @Schema(name = "email", example = "janedoe@email.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(name = "password", example = "Password123")
    private String password;
}
