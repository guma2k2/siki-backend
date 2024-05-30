package com.siki.order.service;

import com.siki.order.dto.OrderDto;
import com.siki.order.dto.OrderPostDto;
import com.siki.order.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    void createOrder(OrderPostDto orderPostDto);
    List<OrderDto> findAll();
    OrderDto findById(Long orderId);
    List<OrderDto> findAllByUserId();
    List<OrderDto> findAllByUserAndStatus(OrderStatus status);

}
