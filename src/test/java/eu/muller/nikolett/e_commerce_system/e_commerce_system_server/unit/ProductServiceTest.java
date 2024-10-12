package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private static final String PRODUCT_NAME = "Product name";

    private static final String PRODUCT_DESC = "Product description";

    private static final Integer PRODUCT_PRICE = 2000;

    private static final Integer PRODUCT_ID = 1;

    @Test
    void validAddProductTest() {
        ProductRequest request = createProductRequest(PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE);
        Product product = createProduct(PRODUCT_ID, PRODUCT_NAME,PRODUCT_DESC, PRODUCT_PRICE);
        ProductResponse response = createProductResponse(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE, product.getCreatedAt());

        doReturn(product).when(productMapper).map(request);
        doReturn(product).when(productRepository).save(product);
        doReturn(response).when(productMapper).map(product);

        ProductResponse productResponse = productService.addProduct(request);

        assertAll(
                () -> assertEquals(PRODUCT_NAME, productResponse.getName()),
                () -> assertEquals(PRODUCT_DESC, productResponse.getDescription()),
                () -> assertEquals(PRODUCT_PRICE, productResponse.getPrice())
        );

    }

    private ProductRequest createProductRequest(String name, String description, Integer price) {
        return ProductRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }

    private Product createProduct(Integer id, String name, String description, Integer price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return product;
    }

    private ProductResponse createProductResponse(Integer id, String name, String description, Integer price, Timestamp createdAt) {
        return new ProductResponse(id, name, description, price, createdAt);
    }
}
