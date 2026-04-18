package com.spring.boot.rest.repo.order;

import com.spring.boot.rest.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}
