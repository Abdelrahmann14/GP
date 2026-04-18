package com.spring.boot.rest.service.order.OrdeServiceImpl;

import com.spring.boot.rest.dto.order.OrderDto;
import com.spring.boot.rest.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void saveOrder(OrderDto orderDto) {

    }

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return null;
    }
}
