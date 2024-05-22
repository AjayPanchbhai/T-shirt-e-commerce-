package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.OrderDTO;
import com.ECommerce.Tshirt.Mappers.OrderMapper;
import com.ECommerce.Tshirt.Models.Order;
import com.ECommerce.Tshirt.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // add order
    @PostMapping("/{userId}")
    public ResponseEntity<OrderDTO> addOrder(@PathVariable long userId, @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderMapper.toOrderDTO(orderService.addOrder(userId, order)));
    }

    // update order
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable long orderId, @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(OrderMapper.toOrderDTO(orderService.updateOrder(orderId, order)));
    }

    // get All Orders
    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.FOUND).body(
                orderService.getAllOrders()
                        .stream()
                        .map(OrderMapper::toOrderDTO)
                        .collect(Collectors.toList())
        );
    }
}
