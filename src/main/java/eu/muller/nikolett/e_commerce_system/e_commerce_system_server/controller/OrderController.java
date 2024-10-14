package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.OrderResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order management related APIs")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Add order", description = "Place an order")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful ordering"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @PostMapping("/orders")
    public ResponseEntity<Void> addOrder(@Validated @RequestBody OrderRequest orderRequest) {
        log.info("Add order: {}", orderRequest);
        orderService.addOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Find order", description = "Find order by given id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "order found"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable(name = "id") Integer id) {
        log.info("Find order by id: {}", id);
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @Operation(summary = "Find users order", description = "Find order by given user id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "orders by user found"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping("/orders/user/{id}")
    public ResponseEntity<List<OrderResponse>> findOrderByUserId(@PathVariable(name = "id") Integer id) {
        log.info("Find orders by user id: {}", id);
        return ResponseEntity.ok(orderService.findOrderByUserId(id));
    }
}
