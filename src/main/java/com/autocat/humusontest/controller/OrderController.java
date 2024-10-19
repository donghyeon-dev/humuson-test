package com.autocat.humusontest.controller;

import com.autocat.humusontest.domain.Order;
import com.autocat.humusontest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Fetch order data from external service and store in Redis
     * @param orderId
     * @return Order
     */
    @PostMapping("/order")
    Order fetchOrderData(@RequestBody Order order) {
        return orderService.fetchOrderData(order);
    }

    /**
     * Get order data from Redis
     * @param id
     * @return Order
     */
    @GetMapping("/orders/{orderId}")
    Order getOrder(@PathVariable("orderId") String orderId) {
        return orderService.getOrder(orderId);
    }

    /**
     * Get all orders from Redis
     * @return List<Order>
     */
    @GetMapping("/orders")
    List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }

}
