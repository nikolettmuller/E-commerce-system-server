package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.ProductNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        var request = createProductRequest(PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE);
        var product = createProduct(PRODUCT_ID, PRODUCT_NAME,PRODUCT_DESC, PRODUCT_PRICE);
        var response = createProductResponse(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE, product.getCreatedAt());

        doReturn(product).when(productMapper).map(request);
        doReturn(product).when(productRepository).save(product);
        doReturn(response).when(productMapper).map(product);

        var productResponse = productService.addProduct(request);

        assertAll(
                () -> assertEquals(PRODUCT_NAME, productResponse.getName()),
                () -> assertEquals(PRODUCT_DESC, productResponse.getDescription()),
                () -> assertEquals(PRODUCT_PRICE, productResponse.getPrice())
        );

    }

    @Test
    void findProductByValidIdTest() {
        var product = Optional.of(createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE));
        var response = createProductResponse(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE, product.get().getCreatedAt());
        var id = 1;

        doReturn(product).when(productRepository).findById(id);
        doReturn(response).when(productMapper).map(product.get());

        var productResponse = productService.findProductById(id);

        assertAll(
                () -> assertEquals(PRODUCT_NAME, productResponse.getName()),
                () -> assertEquals(PRODUCT_DESC, productResponse.getDescription()),
                () -> assertEquals(PRODUCT_PRICE, productResponse.getPrice())
        );

    }

    @Test
    void findProductByInValidIdTest() {
        var id = 1;
        doReturn(Optional.empty()).when(productRepository).findById(id);
        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(id));

    }

    @Test
    void modifyProductTest() {
        var request = createProductRequest("modifiedName", PRODUCT_DESC, PRODUCT_PRICE);
        var product = Optional.of(createProduct(PRODUCT_ID, "modifiedName", PRODUCT_DESC, PRODUCT_PRICE));
        var response = createProductResponse(PRODUCT_ID, "modifiedName", PRODUCT_DESC, PRODUCT_PRICE, product.get().getCreatedAt());

        doReturn(product).when(productRepository).findById(PRODUCT_ID);
        doReturn(response).when(productMapper).map(product.get());
        doReturn(product.get()).when(productRepository).save(product.get());

        var productResponse = productService.modifyProduct(PRODUCT_ID, request);

        assertAll(
                () -> assertEquals("modifiedName", productResponse.getName()),
                () -> assertEquals(PRODUCT_DESC, productResponse.getDescription()),
                () -> assertEquals(PRODUCT_PRICE, productResponse.getPrice()),
                () -> assertEquals(PRODUCT_ID, productResponse.getId())
        );

    }

    @Test
    void deleteTest() {
        var id = 1;
        doNothing().when(productRepository).deleteById(id);
        productService.deleteProduct(id);
        verify(productRepository, times(1)).deleteById(id);

    }

    private ProductRequest createProductRequest(String name, String description, Integer price) {
        return ProductRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }

    private Product createProduct(Integer id, String name, String description, Integer price) {
        var product = new Product();
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
