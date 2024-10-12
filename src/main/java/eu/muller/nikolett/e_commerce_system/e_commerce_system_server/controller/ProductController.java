package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Find product", description = "Find product by given id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "product found"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> findProductById(@Parameter(description = "Product Id") @PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @Operation(summary = "List products", description = "Lists all products")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "product list"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> findProducts() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @Operation(summary = "Modify product", description = "Modifies given product")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful modification"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> modifyProduct(@Parameter(description = "Product Id") @PathVariable(name = "id") Integer id,
                                                               @Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.modifyProduct(id, productRequest));
    }

    @Operation(summary = "Delete product", description = "Deletes given product")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful delete"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@Parameter(description = "Product Id") @PathVariable(name = "id") Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
