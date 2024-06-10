package com.siki.order.controller;

import com.siki.order.dto.OrderDto;
import com.siki.order.dto.OrderGetListDto;
import com.siki.order.dto.OrderPostDto;
import com.siki.order.enums.OrderStatus;
import com.siki.order.service.OrderDetailService;
import com.siki.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    public OrderController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/storefront")
    public ResponseEntity<Long> createOrder(@RequestBody OrderPostDto orderPostDto) {
        Long orderId = orderService.createOrder(orderPostDto);
        return ResponseEntity.ok().body(orderId);
    }

    @GetMapping("/storefront")
    public ResponseEntity<List<OrderDto>> findAllByUserId() {
        List<OrderDto> orderDtos = orderService.findAllByUserId();
        return ResponseEntity.ok().body(orderDtos);
    }

    @GetMapping("/storefront/{orderId}")
    public ResponseEntity<OrderDto> findById(@PathVariable("orderId") Long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        return ResponseEntity.ok().body(orderDto);
    }
    @GetMapping("/backoffice")
    public ResponseEntity<List<OrderGetListDto>> findAll() {
        List<OrderGetListDto> orderDtos = orderService.findAll();
        return ResponseEntity.ok().body(orderDtos);
    }

    @GetMapping("/backoffice/status/{orderStatus}")
    public ResponseEntity<List<OrderGetListDto>> findAllByStatus(@PathVariable("orderStatus") OrderStatus orderStatus) {
        List<OrderGetListDto> orderDtos = orderService.findAllByStatus(orderStatus);
        return ResponseEntity.ok().body(orderDtos);
    }


    @GetMapping("/sold-num/product/{productId}")
    public ResponseEntity<Long> getSoldNumByProduct(
            @PathVariable("productId") Long productId
    ) {
        return ResponseEntity.ok().body(orderDetailService.getSoldNumByProduct(productId));
    }

    @GetMapping("/storefront/status/{orderStatus}")
    public ResponseEntity<List<OrderDto>> findAllByUserIdAndStatus(
            @PathVariable("orderStatus") OrderStatus orderStatus
    ) {
        List<OrderDto> orderDtos = orderService.findAllByUserAndStatus(orderStatus);
        return ResponseEntity.ok().body(orderDtos);
    }
    @PutMapping("/storefront/{orderId}/status/{orderStatus}")
    public ResponseEntity<Void> updateStatusOrderById(
            @PathVariable("orderId") Long orderId,
            @PathVariable("orderStatus") OrderStatus orderStatus
    ) {
        orderService.updateStatusOrderById(orderId, orderStatus);
        return ResponseEntity.noContent().build();
    }
}
