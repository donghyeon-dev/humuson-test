package com.autocat.humusontest.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("order")
public class Order {

    @Id
    private String orderId;

    private String customerName;

    private LocalDate orderDate;

    private OrderStatus orderStatus;
}

