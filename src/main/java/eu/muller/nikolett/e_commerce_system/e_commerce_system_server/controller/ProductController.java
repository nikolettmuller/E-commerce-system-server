package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management related APIs")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Add product", description = "Add product with details")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful product add"),
                            @ApiResponse(responseCode = "400", description = "bad request")})
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> addProduct(@Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.addProduct(productRequest));
    }
}
