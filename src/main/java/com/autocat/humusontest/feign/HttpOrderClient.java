package com.autocat.humusontest.feign;

import com.autocat.humusontest.config.FeignConfig;
import com.autocat.humusontest.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "external-order-fetch", url="https://dummyjson.com/c/5bae-54fc-4c88-a89c"
        , configuration = FeignConfig.class)
public interface HttpOrderClient {

    @GetMapping("/orders/{orderId}")
    Order fetchOrderData(@PathVariable("orderId") String orderId);

    @GetMapping("/orders/")
    List<Order> fetchOrderDataByOrderDate(@RequestParam("orderDate") String orderDate);

}
