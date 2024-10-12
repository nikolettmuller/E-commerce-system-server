package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ProductResponse {

    @Schema(name = "id", example = "1")
    private Integer id;

    @Schema(name = "name", example = "Product")
    private String name;

    @Schema(name = "description", example = "Product detailed description")
    private String description;

    @Schema(name = "price", example = "1000")
    private Integer price;

    @Schema(name = "createdAt", example = "2024-10-12T14:10:05.591")
    private Timestamp createdAt;
}
