package com.spring.boot.rest.service.order;
import com.spring.boot.rest.dto.order.OrderDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface OrderService {
    void saveOrder(@Valid OrderDto orderDto);

    ResponseEntity<List<OrderDto>> getAllOrders();
}
