package com.siki.order.dto;

import com.siki.order.enums.OrderStatus;
import com.siki.order.model.Order;
import com.siki.order.utils.DateFormatter;

import java.util.List;

public record OrderGetListDto(
         Long id,
         String receiverPhoneNumber,
         String receiverAddress,
         String receiverName,
         String note,
         String createdAt,
         OrderStatus status,
         List<OrderDetailDto> orderDetails,
         CustomerDto customer
) {
    public static OrderGetListDto fromModel(Order order, List<OrderDetailDto> orderDetails, CustomerDto customer) {
        return new OrderGetListDto(order.getId(),
                order.getReceiverPhoneNumber(),
                order.getReceiverAddress(),
                order.getReceiverName(),
                order.getNote(),
                DateFormatter.convertLocalDateTime(order.getCreatedAt()),
                order.getStatus(),
                orderDetails,
                customer
        );
    }
}
