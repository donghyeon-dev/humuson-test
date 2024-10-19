package com.autocat.humusontest.util;

import com.autocat.humusontest.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void storeOrder(String orderId, Order order) {
        redisTemplate.opsForValue().set(orderId, order);
    }

    public Order getOrder(String orderId) {
        return (Order) redisTemplate.opsForValue().get(orderId);
    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("*");

        if(ObjectUtils.isEmpty(keys)){
            return orders;
        }
        for(String key : keys){
            orders.add((Order) redisTemplate.opsForValue().get(key));
        }

        return orders;
    }

    public void deleteOrder(String orderId) {
        redisTemplate.delete(orderId);
    }

}