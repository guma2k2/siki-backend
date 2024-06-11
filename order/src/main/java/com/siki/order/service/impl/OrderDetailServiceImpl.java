package com.siki.order.service.impl;

import com.siki.order.dto.ProductVariantDto;
import com.siki.order.enums.OrderStatus;
import com.siki.order.repository.OrderDetailRepository;
import com.siki.order.service.OrderDetailService;
import com.siki.order.service.client.ProductFeignClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    private final ProductFeignClient productFeignClient;


    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ProductFeignClient productFeignClient) {
        this.orderDetailRepository = orderDetailRepository;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public Long getSoldNumByProduct(Long productId) {
        OrderStatus success = OrderStatus.SUCCESS;
        return orderDetailRepository.getSoldNumByProduct(productId, success);
    }

    @Override
    public List<ProductVariantDto> getTopProductBestSeller(int limit) {

        List<Long> productIds = orderDetailRepository.findTopProductsByQuantity();

        List<Long> productIdsActual = productIds.stream().limit(limit).toList();

        List<ProductVariantDto> productVariantDtos = productIdsActual.stream().map(id -> {
            ProductVariantDto productVariantDto = productFeignClient.getByProductId(id).getBody();
            return productVariantDto;
        }).toList();
        return productVariantDtos;
    }
}
