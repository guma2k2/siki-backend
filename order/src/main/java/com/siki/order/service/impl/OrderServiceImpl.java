package com.siki.order.service.impl;

import com.siki.order.dto.OrderDto;
import com.siki.order.enums.OrderStatus;
import com.siki.order.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public void createOrder() {

    }

    @Override
    public List<OrderDto> findAll() {
        return null;
    }

    @Override
    public OrderDto findById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderDto> findAllByUserId() {
        return null;
    }

    @Override
    public List<OrderDto> findAllByUserAndStatus(OrderStatus status) {
        return null;
    }
}
