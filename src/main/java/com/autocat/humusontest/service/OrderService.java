package com.autocat.humusontest.service;

import com.autocat.humusontest.domain.Order;
import com.autocat.humusontest.feign.ExternalOrderClient;
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

    private final ExternalOrderClient externalOrderClient;
    private final OrderRedisRepository orderRedisRepository;

    public Order fetchOrderData(Order order) {
        if(Objects.isNull(order)){
            log.error("Invalid order {}", order);
            throw new InvalidParameterException("Invalid order");
        }
        String orderId = order.getOrderId();
        if(!StringUtils.hasText(orderId)){
            log.error("Invalid orderId {}", orderId);
            throw new InvalidParameterException("Invalid order id");
        }

        order = externalOrderClient.fetchOrderData(orderId);
        orderRedisRepository.save(order);

        return order;
    }

    public Order getOrder(String orderId) {
        if (!StringUtils.hasText(orderId)) {
            log.error("Invalid orderId {}", orderId);
            throw new InvalidParameterException("Invalid order id");
        }

        Optional<Order> optionalOrder = orderRedisRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        } else {
            log.error("Order not found. Fetching from external service. Order id: {}", orderId);
            throw new InvalidParameterException("Order not found");
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Iterable<Order> ordersInRedis=  orderRedisRepository.findAll();

        if(ObjectUtils.isEmpty(ordersInRedis)){
            log.error("No orders found");
            throw new InvalidParameterException("No orders found");
        };
        for(Order order: ordersInRedis){
            if(!ObjectUtils.isEmpty(order)){
                orders.add(order);
            }
        }

        return orders;
    }
}
