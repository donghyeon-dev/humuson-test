package com.autocat.humusontest.feign;

import com.autocat.humusontest.config.FeignConfig;
import com.autocat.humusontest.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "external-order-storage", url = "https://external.order.storage.com"
        , configuration = FeignConfig.class)
public interface OrderStorageClient {

    @GetMapping("/order/{orderId}")
    Order fetchOrderData(@PathVariable String orderId);
    @PostMapping("/order/")
    Order sendOrderData(@RequestBody Order order);

}
