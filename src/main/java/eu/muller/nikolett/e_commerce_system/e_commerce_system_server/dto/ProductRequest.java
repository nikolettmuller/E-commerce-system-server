package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Schema(name = "name", example = "Product")
    private String name;

    @NotBlank(message = "Product description is required")
    @Schema(name = "description", example = "Product detailed description")
    private String description;

    @NotNull(message = "Product price is required")
    @Schema(name = "price", example = "1000")
    private Integer price;
}
