package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
public class OrderResponse {

    @Schema(name = "id", example = "1")
    private Integer id;

    @Schema(name = "userId", example = "1")
    private Integer userId;

    @Schema(name = "createdAt", example = "2024-10-12T14:10:05.591")
    private Timestamp createdAt;

    @Schema(name = "orderItemResponses")
    private Set<OrderItemResponse> orderItemResponses;
}
