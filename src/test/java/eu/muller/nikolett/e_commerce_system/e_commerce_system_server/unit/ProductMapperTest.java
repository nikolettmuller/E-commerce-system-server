package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapperImpl();

    @Test
    void productMapperByProductRequestTest() {
        var request = ProductRequest.builder()
                .name("Product name")
                .description("Product description")
                .price(1000)
                .build();

        var product = productMapper.map(request);

        Assertions.assertAll(
                () -> Assertions.assertEquals(request.getName(), product.getName()),
                () -> Assertions.assertEquals(request.getDescription(), product.getDescription()),
                () -> Assertions.assertEquals(request.getPrice(), product.getPrice()),
                () -> Assertions.assertNotNull(product.getCreatedAt())
        );

    }

    @Test
    void productMapperByProductTest() {
        var product = new Product();
        product.setId(1);
        product.setName("Product name");
        product.setDescription("Product description");
        product.setPrice(1000);
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        var response = productMapper.map(product);

        Assertions.assertAll(
                () -> Assertions.assertEquals(product.getName(), response.getName()),
                () -> Assertions.assertEquals(product.getDescription(), response.getDescription()),
                () -> Assertions.assertEquals(product.getPrice(), response.getPrice()),
                () -> Assertions.assertEquals(product.getCreatedAt(), response.getCreatedAt())
        );

    }
}
