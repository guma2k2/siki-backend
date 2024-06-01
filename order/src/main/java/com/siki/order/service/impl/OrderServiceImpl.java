package com.siki.order.service.impl;

import com.siki.order.dto.*;
import com.siki.order.enums.OrderStatus;
import com.siki.order.model.Order;
import com.siki.order.model.OrderDetail;
import com.siki.order.repository.OrderDetailRepository;
import com.siki.order.repository.OrderRepository;
import com.siki.order.service.OrderService;
import com.siki.order.service.client.ProductFeignClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final ProductFeignClient productFeignClient;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ProductFeignClient productFeignClient) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public void createOrder(OrderPostDto orderPostDto) {
        Order order = Order.builder()
                .note(orderPostDto.note())
                .receiverAddress(orderPostDto.receiverAddress())
                .receiverName(orderPostDto.receiverName())
                .receiverPhoneNumber(orderPostDto.receiverPhoneNumber())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        Order savedOrder = orderRepository.saveAndFlush(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailPostDto orderPostDetail: orderPostDto.orderDetails()) {
            OrderDetail orderDetail = OrderDetail
                    .builder()
                    .order(savedOrder)
                    .productId(orderPostDetail.productId())
                    .quantity(orderPostDetail.quantity())
                    .price(orderPostDetail.price())
                    .build();
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
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
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            List<OrderDetailDto> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductId();
                ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
                return OrderDetailDto.fromModel(orderDetail, productVariantDto);
            }).toList();
            return OrderDto.fromModel(order, orderDetailDtos);
        }).toList();
        return orderDtos;
    }

    @Override
    public List<OrderDto> findAllByUserAndStatus(OrderStatus status) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders = orderRepository.findAllByUserIdAndStatus(userId, status);
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            List<OrderDetailDto> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductId();
                ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
                return OrderDetailDto.fromModel(orderDetail, productVariantDto);
            }).toList();
            return OrderDto.fromModel(order, orderDetailDtos);
        }).toList();
        return orderDtos;
    }

    @Override
    @Transactional
    public void updateStatusOrderById(Long orderId, OrderStatus orderStatus) {
        orderRepository.updateStatusById(orderId, orderStatus   );
    }
}
