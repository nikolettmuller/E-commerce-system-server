package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.integration;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller.ProductController;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.ProductNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @BeforeEach
    public void initRecord() {
        productRepository.save(createProduct("test product1", "test product desc1", 1000));
        productRepository.save(createProduct("test product2", "test product desc2", 2000));
    }

    @AfterEach
    public void clearRecord() {
        productRepository.deleteAll();
    }

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

    @Test
    void listProductTest() {

        ResponseEntity<List<ProductResponse>> response = productController.findProducts();

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(2, response.getBody().size())
        );
    }

    @Test
    void findProductByValidIdTest() {
        int validProductId = productRepository.findAll().get(0).getId();
        ResponseEntity<ProductResponse> response = productController.findProductById(validProductId);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    void findProductByInvalidIdTest() {
        int invalidProductId = -1;
        assertThrows(ProductNotFoundException.class, () -> productController.findProductById(invalidProductId));
    }

    @Test
    void modifyProductTest() {
        int productId = productRepository.findAll().get(0).getId();
        ProductRequest productRequest = ProductRequest.builder()
                .price(1001)
                .description("modfiedDesc")
                .name("modifiedName")
                .build();
        ResponseEntity<ProductResponse> response = productController.modifyProduct(productId, productRequest);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("modfiedDesc", response.getBody().getDescription()),
                () -> assertEquals("modifiedName", response.getBody().getName()),
                () -> assertEquals(1001, response.getBody().getPrice())
        );
    }

    @Test
    void deleteProductTest() {
        int productId = productRepository.findAll().get(0).getId();
        productController.deleteProductById(productId);
        assertEquals(1, productRepository.findAll().size());
    }


    private ProductRequest createProductRequest(String name, String description, Integer price) {
        return ProductRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }

    private Product createProduct(String name, String description, Integer price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return product;
    }
}
