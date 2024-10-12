package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderItemRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Order;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.OrderItem;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    //Order map(OrderRequest orderRequest);

   // OrderItem map(OrderItemRequest orderItemRequest);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    Order createOrderForUser(User user);

    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "orderItemRequest.price")
    OrderItem createOrderItemForProduct(OrderItemRequest orderItemRequest, Order order, Product product);
}
