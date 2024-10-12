package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OrderRequest {

    @NotNull(message = "User id is required")
    @Schema(name = "userId", example = "1")
    private Integer userId;

    @NotEmpty(message = "Order item(s) is required")
    @Schema(name = "orderItemRequests")
    private Set<OrderItemRequest> orderItemRequests;
}
