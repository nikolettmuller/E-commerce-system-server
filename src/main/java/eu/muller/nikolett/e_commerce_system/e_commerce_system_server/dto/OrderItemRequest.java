package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemRequest {

    @NotNull(message = "Product id is required")
    @Schema(name = "productId", example = "1")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @Schema(name = "quantity", example = "3")
    private Integer quantity;

}
