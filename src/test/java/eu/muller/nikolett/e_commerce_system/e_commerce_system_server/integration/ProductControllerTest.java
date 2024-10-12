package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.integration;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller.ProductController;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductControllerTest {

    @Autowired
    private ProductController productController;


    @Test
    void validAddProductTest() {
        ProductRequest request = createProductRequest("Product name", "Product description", 1000);

        ResponseEntity<ProductResponse> response = productController.addProduct(request);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(request.getName(), response.getBody().getName())
        );
    }

    private ProductRequest createProductRequest(String name, String description, Integer price) {
        return ProductRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
