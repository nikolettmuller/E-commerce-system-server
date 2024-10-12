package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class OrderItemResponse {

    @Schema(name = "id", example = "1")
    private Integer id;

    @Schema(name = "orderId", example = "1")
    private Integer orderId;

    @Schema(name = "productId", example = "1")
    private Integer productId;

    @Schema(name = "quantity", example = "3")
    private Integer quantity;

    @Schema(name = "price", example = "2500")
    private Integer price;
}
