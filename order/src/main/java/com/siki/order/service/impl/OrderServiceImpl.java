package com.siki.order.service.impl;

import com.siki.order.dto.*;
import com.siki.order.enums.OrderStatus;
import com.siki.order.model.Order;
import com.siki.order.model.OrderDetail;
import com.siki.order.repository.OrderDetailRepository;
import com.siki.order.repository.OrderRepository;
import com.siki.order.service.OrderService;
import com.siki.order.service.client.CustomerFeignClient;
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

    private final CustomerFeignClient customerFeignClient;
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ProductFeignClient productFeignClient, CustomerFeignClient customerFeignClient) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productFeignClient = productFeignClient;
        this.customerFeignClient = customerFeignClient;
    }

    @Override
    public Long createOrder(OrderPostDto orderPostDto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = Order.builder()
                .note(orderPostDto.note())
                .receiverAddress(orderPostDto.receiverAddress())
                .receiverName(orderPostDto.receiverName())
                .receiverPhoneNumber(orderPostDto.receiverPhoneNumber())
                .status(OrderStatus.PENDING)
                .userId(userId)
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
        return savedOrder.getId();
    }

    @Override
    public List<OrderGetListDto> findAll() {
        List<Order> orders = orderRepository.findAllCustom();
        List<OrderGetListDto> orderDtos = orders.stream().map(order -> {
            CustomerDto customerDto = customerFeignClient.getCustomerById(order.getUserId()).getBody();
            List<OrderDetailDto> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductId();
                ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
                return OrderDetailDto.fromModel(orderDetail, productVariantDto);
            }).toList();
            return OrderGetListDto.fromModel(order, orderDetailDtos, customerDto);
        }).toList();
        return orderDtos;
    }

    @Override
    public OrderDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        List<OrderDetailDto> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
            Long productId = orderDetail.getProductId();
            ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
            return OrderDetailDto.fromModel(orderDetail, productVariantDto);
        }).toList();

        return OrderDto.fromModel(order, orderDetailDtos);
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
        orderRepository.updateStatusById(orderId, orderStatus);
    }

    @Override
    public List<OrderGetListDto> findAllByStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findAllByStatus(orderStatus);
        List<OrderGetListDto> orderDtos = orders.stream().map(order -> {
            CustomerDto customerDto = customerFeignClient.getCustomerById(order.getUserId()).getBody();
            List<OrderDetailDto> orderDetailDtos = order.getOrderDetails().stream().map(orderDetail -> {
                Long productId = orderDetail.getProductId();
                ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
                return OrderDetailDto.fromModel(orderDetail, productVariantDto);
            }).toList();
            return OrderGetListDto.fromModel(order, orderDetailDtos, customerDto);
        }).toList();
        return orderDtos;
    }
}
