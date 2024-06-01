package com.siki.order.controller;

import com.siki.order.dto.OrderDto;
import com.siki.order.dto.OrderPostDto;
import com.siki.order.enums.OrderStatus;
import com.siki.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/storefront")
    public ResponseEntity<Void> createOrder(@RequestBody OrderPostDto orderPostDto) {
        orderService.createOrder(orderPostDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/storefront")
    public ResponseEntity<List<OrderDto>> findAllByUserId() {
        List<OrderDto> orderDtos = orderService.findAllByUserId();
        return ResponseEntity.ok().body(orderDtos);
    }

    @GetMapping("/storefront/status/{orderStatus}")
    public ResponseEntity<List<OrderDto>> findAllByUserIdAndStatus(
            @RequestParam OrderStatus orderStatus
    ) {
        List<OrderDto> orderDtos = orderService.findAllByUserAndStatus(orderStatus);
        return ResponseEntity.ok().body(orderDtos);
    }
    @PutMapping("/storefront/{orderId}/status/{orderStatus}")
    public ResponseEntity<Void> updateStatusOrderById(
            @PathVariable("orderId") Long orderId,
            @RequestParam OrderStatus orderStatus
    ) {
        orderService.updateStatusOrderById(orderId, orderStatus);
        return ResponseEntity.noContent().build();
    }
}
