package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Order;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.OrderItem;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.ProductNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.UserNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.OrderMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    private static final String USER_NOT_FOUND = "User not found: %s.";

    @Override
    public void addOrder(OrderRequest orderRequest) {
        User user = findUserById(orderRequest.getUserId());
        Order order = orderMapper.createOrderForUser(user);

        Set<OrderItem> orderItems = createOrderItems(orderRequest.getOrderItemRequests(), order);
        order.setOrderItems(orderItems);

        user.getOrders().add(order);
        userRepository.save(user);
    }

    private User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, userId)));
    }


    private Set<OrderItem> createOrderItems(Set<OrderItemRequest> orderItemRequests, Order order) {
        return orderItemRequests.stream()
                .map(orderItemRequest -> createOrderItem(orderItemRequest, order))
                .collect(Collectors.toSet());
    }

    private OrderItem createOrderItem(OrderItemRequest orderItemRequest, Order order) {
        Product product = findProductById(orderItemRequest.getProductId());
        return orderMapper.createOrderItemForProduct(orderItemRequest, order, product);
    }

    private Product findProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public OrderResponse findOrderById(Integer id) {
        return null;
    }

    @Override
    public Collection<OrderResponse> findOrderByUserId(Integer id) {
        return List.of();
    }
}
