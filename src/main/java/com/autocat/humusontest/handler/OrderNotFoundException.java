package com.autocat.humusontest.handler;

public class OrderNotFoundException extends RuntimeException{

        public OrderNotFoundException(String orderId) {
            super("Order with id " + orderId + " not found");
        }
}
