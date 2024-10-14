package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Order;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.OrderItem;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.OrderNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.ProductNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.UserNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.OrderMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.OrderRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    private static final String USER_NOT_FOUND = "User not found: %s.";

    private static final String ORDER_NOT_FOUND = "Order not found: %s.";

    @Override
    public void addOrder(OrderRequest orderRequest) {
        var user = findUserById(orderRequest.getUserId());
        var order = orderMapper.createOrderForUser(user);

        var orderItems = createOrderItems(orderRequest.getOrderItemRequests(), order);
        order.setOrderItems(orderItems);

        user.getOrders().add(order);
        userRepository.save(user);
    }

    @Override
    public OrderResponse findOrderById(Integer id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND, id)));

        var orderItemResponse = order.getOrderItems().stream()
                .map(orderItem -> orderMapper.map(orderItem, order, orderItem.getProduct()))
                .collect(Collectors.toSet());

       return orderMapper.map(order, order.getUser(), orderItemResponse);
    }

    @Override
    public List<OrderResponse> findOrderByUserId(Integer id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));
        var orders = user.getOrders();
        return orders.stream().map(order -> findOrderById(order.getId().intValue())).toList();
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
        var product = findProductById(orderItemRequest.getProductId());
        return orderMapper.createOrderItemForProduct(orderItemRequest, order, product);
    }

    private Product findProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
