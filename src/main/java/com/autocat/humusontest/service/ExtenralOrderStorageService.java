package com.autocat.humusontest.service;

import com.autocat.humusontest.domain.Order;
import com.autocat.humusontest.feign.OrderStorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtenralOrderStorageService implements ExternalOrderService{

    private final OrderStorageClient orderStorageClient;

    @Override
    public Order fetchOrderData(String ordrId) {
        return orderStorageClient.fetchOrderData(ordrId);
    }

    @Override
    public void sendOrderData(Order order) {
        orderStorageClient.sendOrderData(order);
    }
}
