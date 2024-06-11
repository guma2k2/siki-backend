package com.siki.order.service;

import com.siki.order.dto.OrderDto;
import com.siki.order.dto.OrderGetListDto;
import com.siki.order.dto.OrderPostDto;
import com.siki.order.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Long createOrder(OrderPostDto orderPostDto);
    List<OrderGetListDto> findAll();
    OrderDto findById(Long orderId);
    List<OrderDto> findAllByUserId();
    List<OrderDto> findAllByUserAndStatus(OrderStatus status);
    void updateStatusOrderById(Long orderId, OrderStatus orderStatus);
    List<OrderGetListDto> findAllByStatus(OrderStatus orderStatus);
}
