package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.integration;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller.OrderController;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.OrderNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.OrderRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User testUser;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testUser = createUser("Test User", "test@test.com", "pass", UserRole.ADMIN);
        testProduct = createProduct("Test product name", "Test desc", 1000);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void validPlaceOrderTest() {
        var orderRequest = createOrderRequest(testUser.getId(), Set.of(createOrderItemRequest(testProduct.getId(), 2)));
        var response = orderController.addOrder(orderRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void validMultiplePlaceOrderTest() {
        var orderRequest = createOrderRequest(testUser.getId(), Set.of(createOrderItemRequest(testProduct.getId(), 2)));
        orderController.addOrder(orderRequest);
        orderController.addOrder(orderRequest);
        assertEquals(2, orderRepository.findAll().size());
    }

    @Test
    void findOrderTest() {
        var orderRequest = createOrderRequest(testUser.getId(), Set.of(createOrderItemRequest(testProduct.getId(), 2)));
        orderController.addOrder(orderRequest);

        var orderId = orderRepository.findAll().get(0).getId().intValue();
        var response = orderController.findOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser.getId(), response.getBody().getUserId());
    }

    @Test
    void invalidIdTest() {
        assertThrows(OrderNotFoundException.class, () -> orderController.findOrderById(-1));
    }

    @Test
    void findOrderByUserIdTest() {
        var orderRequest = createOrderRequest(testUser.getId(), Set.of(createOrderItemRequest(testProduct.getId(), 2)));
        orderController.addOrder(orderRequest);

        var response = orderController.findOrderByUserId(testUser.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser.getId(), response.getBody().get(0).getUserId());
    }

    private User createUser(String name, String email, String password, UserRole role) {
        var user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setRole(role);
        return userRepository.save(user);
    }

    private Product createProduct(String name, String description, int price) {
        var product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return productRepository.save(product);
    }

    private OrderRequest createOrderRequest(Integer userId, Set<OrderItemRequest> orderItemRequests) {
        return OrderRequest.builder()
                .userId(userId)
                .orderItemRequests(orderItemRequests)
                .build();
    }

    private OrderItemRequest createOrderItemRequest(Integer productId, Integer quantity) {
        return OrderItemRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
