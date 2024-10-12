package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Void> addOrder(@Validated @RequestBody OrderRequest orderRequest) {
        orderService.addOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/orders/user/{id}")
    public ResponseEntity<Collection<OrderResponse>> findOrderByUserId(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(orderService.findOrderByUserId(id));
    }
}
