package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.unit;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.*;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.OrderMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.OrderRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepository orderRepository;

    private static final String USER_NAME = "Test User";

    private static final String USER_EMAIL = "testuser@testuser.com";

    private static final String USER_PASSWORD = "password123";

    private static final String PRODUCT_NAME = "Product name";

    private static final String PRODUCT_DESC = "Product description";

    private static final Integer PRODUCT_PRICE = 2000;

    private static final Integer PRODUCT_ID = 1;

    @Test
    void validAddOrderTest() {
        Integer userId = 1;
        Optional<User> user = Optional.of(createUser(userId, USER_NAME, UserRole.USER, USER_EMAIL, USER_PASSWORD));
        Optional<Product> product = Optional.of(createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE));
        OrderItem orderItem = createOrderItems(1L, product.get(), createOrder(1L, user.get(), Set.of()), 2, 4000);
        Order order = createOrder(1L, user.get(), Set.of(orderItem));

        OrderItemRequest orderItemRequest = createOrderItemRequest(PRODUCT_ID, 2);
        OrderRequest orderRequest = createOrderRequest(user.get().getId(), Set.of(orderItemRequest));

        doReturn(user).when(userRepository).findById(userId);
        doReturn(order).when(orderMapper).createOrderForUser(user.get());
        doReturn(user.get()).when(userRepository).save(user.get());
        doReturn(product).when(productRepository).findById(PRODUCT_ID);
        doReturn(orderItem).when(orderMapper).createOrderItemForProduct(orderItemRequest, order, product.get());

        orderService.addOrder(orderRequest);

        verify(userRepository, times(1)).save(user.get());
    }

    @Test
    void findOrderByIdTest() {
        int orderId = 1;
        User user = createUser(1, USER_NAME, UserRole.USER, USER_EMAIL, USER_PASSWORD);
        Product product = createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESC, PRODUCT_PRICE);
        OrderItem orderItem = createOrderItems(1L, product, createOrder(1L, user, Set.of()), 2, 4000);
        Optional<Order> order = Optional.of(createOrder(1L, user, Set.of(orderItem)));
        OrderItemResponse orderItemResponse = new OrderItemResponse(1, orderId, product.getId(), 10, 1000);
        OrderResponse orderResponse = new OrderResponse(1, user.getId(), Timestamp.valueOf(LocalDateTime.now()), Set.of(orderItemResponse));

        doReturn(order).when(orderRepository).findById(orderId);
        doReturn(orderItemResponse).when(orderMapper).map(orderItem, order.get(), product);
        doReturn(orderResponse).when(orderMapper).map(order.get(), user, Set.of(orderItemResponse));

        OrderResponse response = orderService.findOrderById(orderId);

        verify(orderMapper, times(1)).map(order.get(), user, Set.of(orderItemResponse));
        assertEquals(orderResponse.getId(), response.getId());

    }

    private User createUser(Integer id, String name, UserRole role, String email, String password) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    private Order createOrder(Long id, User user, Set<OrderItem> orderItems) {
        Order order = new Order();
        order.setId(id);
        order.setUser(user);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        order.setOrderItems(orderItems);
        return order;
    }

    private OrderItem createOrderItems(Long id, Product product,Order order, Integer quantity, Integer price) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(price);
        return orderItem;
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
