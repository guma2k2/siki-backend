package com.siki.order.dto;

import com.siki.order.enums.OrderStatus;
import com.siki.order.model.Order;
import com.siki.order.model.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
         Long id,
         String receiverPhoneNumber,
         String receiverAddress,
         String receiverName,
         String note,
         String createdAt,
         OrderStatus status,
         List<OrderDetailDto> orderDetails
) {
    public static OrderDto fromModel(Order order, List<OrderDetailDto> orderDetails) {
        return new OrderDto(order.getId(),
                order.getReceiverPhoneNumber(),
                order.getReceiverAddress(),
                order.getReceiverName(),
                order.getNote(),
                order.getCreatedAt().toString(),
                order.getStatus(),
                orderDetails);
    }
}
