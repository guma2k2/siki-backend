package com.siki.order.service.impl;

import com.siki.order.repository.OrderDetailRepository;
import com.siki.order.service.OrderDetailService;

public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;


    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Long getSoldNumByProduct(Long productId) {
        return orderDetailRepository.getSoldNumByProduct(productId);
    }
}
