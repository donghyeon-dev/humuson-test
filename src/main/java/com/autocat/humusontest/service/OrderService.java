package com.autocat.humusontest.service;

import com.autocat.humusontest.domain.Order;
import com.autocat.humusontest.handler.InvalidOrderException;
import com.autocat.humusontest.handler.OrderNotFoundException;
import com.autocat.humusontest.repository.OrderRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRedisRepository orderRedisRepository;
    private final HttpExternalOrderService externalOrderService;
    private final ExtenralOrderStorageService extenralOrderStorageService;

    public Order fetchOrderData(Order order) {
        if(Objects.isNull(order)){
            log.error("order is empty");
            throw new InvalidOrderException("Invalid order");
        }
        String orderId = order.getOrderId();
        if(!StringUtils.hasText(orderId)){
            log.error("orderId is empty");
            throw new InvalidOrderException("Invalid order");
        }

        order = externalOrderService.fetchOrderData(orderId);
        orderRedisRepository.save(order);
        externalOrderService.sendOrderData(order);

        return order;
    }

    public Order getOrder(String orderId) {
        if (!StringUtils.hasText(orderId)) {
            log.error("orderId is empty");
            throw new InvalidOrderException("Invalid order");
        }

        Optional<Order> optionalOrder = orderRedisRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        } else {
            log.error("Order not found. Fetching from external service. Order id: {}", orderId);
            throw new OrderNotFoundException(orderId);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Iterable<Order> ordersInRedis=  orderRedisRepository.findAll();

        if(ObjectUtils.isEmpty(ordersInRedis)){
            log.error("No orders found");
            throw new OrderNotFoundException("NOTHING");
        };
        ordersInRedis.forEach(order -> {
            if(!ObjectUtils.isEmpty(order)){
                orders.add(order);
            }
        });

        return orders;
    }
}
