package com.autocat.humusontest.repository;

import com.autocat.humusontest.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRedisRepository extends CrudRepository<Order, String> {
}
