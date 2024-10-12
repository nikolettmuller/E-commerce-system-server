package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    @Schema(name = "token", example = "gheetrHWHDdh8TCHDSJijiuwDJZ75ID3URMECG")
    private String token;
}
