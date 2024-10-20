package com.autocat.humusontest.service;

import com.autocat.humusontest.domain.Order;

public interface ExternalOrderService {

    Order fetchOrderData(String ordrId);
    void sendOrderData(Order order);
}
