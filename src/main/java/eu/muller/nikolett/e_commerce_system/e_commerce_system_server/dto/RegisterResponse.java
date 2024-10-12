package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {

    @Schema(name = "name", example = "Jane Doe")
    private String name;

}
