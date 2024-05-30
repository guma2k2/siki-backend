package com.siki.order.dto;

import com.siki.order.model.Order;
import com.siki.order.model.OrderDetail;

public record OrderDetailDto(
         Long id,


         ProductVariantDto productVariantDto,

         Double price,

         int quantity
) {
    public static OrderDetailDto fromModel(OrderDetail orderDetail, ProductVariantDto productVariantDto) {
        return new OrderDetailDto(orderDetail.getId(), productVariantDto, productVariantDto.price(), orderDetail.getQuantity());
    }
}
