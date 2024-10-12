package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;

import java.util.Collection;

public interface OrderService {

    void addOrder(OrderRequest orderRequest);

    OrderResponse findOrderById(Integer id);

    Collection<OrderResponse> findOrderByUserId(Integer id);
}
