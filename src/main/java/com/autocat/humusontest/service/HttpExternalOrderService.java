package com.autocat.humusontest.service;

import com.autocat.humusontest.domain.Order;
import com.autocat.humusontest.feign.HttpOrderClient;
import com.autocat.humusontest.handler.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpExternalOrderService implements ExternalOrderService{

    private final HttpOrderClient httpOrderClient;

    @Override
    public Order fetchOrderData(String orderId) {
        Optional<Order> optionalOrder =  Optional.ofNullable(httpOrderClient.fetchOrderData(orderId));
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Override
    public void sendOrderData(Order order) {

    }
}
