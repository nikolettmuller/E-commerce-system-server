package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Order;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.OrderItem;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "createdAt", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    OrderResponse map(Order order, User user, Set<OrderItemResponse> orderItemResponses);

    @Mapping(target = "id", source = "orderItem.id")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "price", source = "orderItem.price")
    OrderItemResponse map(OrderItem orderItem, Order order, Product product);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    Order createOrderForUser(User user);

    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", expression = "java(product.getPrice() * orderItemRequest.getQuantity())")
    OrderItem createOrderItemForProduct(OrderItemRequest orderItemRequest, Order order, Product product);
}
