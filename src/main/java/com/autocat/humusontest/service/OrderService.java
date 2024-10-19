package com.autocat.humusontest.service;

import com.autocat.humusontest.domain.Order;
import com.autocat.humusontest.feign.ExternalOrderClient;
import com.autocat.humusontest.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ExternalOrderClient externalOrderClient;
    private final RedisUtil redisUtil;

    public Order fetchOrderData(String orderId) {
        if(!StringUtils.hasText(orderId)){
            log.error("Invalid orderId {}", orderId);
            throw new InvalidParameterException("Invalid order id");
        }

        Order order = externalOrderClient.fetchOrderData(orderId);
        redisUtil.storeOrder(orderId, order);

        return order;
    }

    public Order getOrder(String orderId) {
        if (!StringUtils.hasText(orderId)) {
            log.error("Invalid orderId {}", orderId);
            throw new InvalidParameterException("Invalid order id");
        }

        Optional<Order> optionalOrder = Optional.ofNullable(redisUtil.getOrder(orderId));
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        } else {
            log.error("Order not found. Fetching from external service. Order id: {}", orderId);
            throw new InvalidParameterException("Order not found");
        }
    }

    public List<Order> getAllOrders() {
        if(Objects.isNull(redisUtil.getAllOrders())){
            log.error("No orders found");
            throw new InvalidParameterException("No orders found");
        }

        Optional<List<Order>> optionalOrders = Optional.ofNullable(redisUtil.getAllOrders());
        if(optionalOrders.isPresent()){
            return optionalOrders.get();
        } else {
            log.error("No orders found");
            throw new InvalidParameterException("No orders found");
        }

    }
}
