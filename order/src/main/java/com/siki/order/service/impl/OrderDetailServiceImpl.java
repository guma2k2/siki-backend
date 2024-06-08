package com.siki.order.service.impl;

import com.siki.order.enums.OrderStatus;
import com.siki.order.repository.OrderDetailRepository;
import com.siki.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;


    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Long getSoldNumByProduct(Long productId) {
        OrderStatus success = OrderStatus.SUCCESS;
        return orderDetailRepository.getSoldNumByProduct(productId, success);
    }
}
