package com.autocat.humusontest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    PENDING, // 처리중
    SHIPPED, // 배송중
    DELIVERED, // 배송완료
    CANCELLED // 취소됨
}
