package com.spring.boot.rest.controller.order;
import com.spring.boot.rest.dto.order.OrderDto;
import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.service.order.OrderService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/Orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/creatOrder")
    public ResponseEntity<Void> creatOrder(@RequestBody @Valid OrderDto orderDto) throws IdException, IdNullException {
        orderService.saveOrder(orderDto);
        return ResponseEntity.created(URI.create("/Orders/creat-Order")).build();
    }

    @GetMapping("/getAllOrdes")
    public ResponseEntity<List<OrderDto>> getAllOrders() throws SystemException {
        return orderService.getAllOrders();
    }





}
