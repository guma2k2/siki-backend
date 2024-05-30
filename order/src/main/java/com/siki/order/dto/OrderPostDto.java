package com.siki.order.dto;

import java.util.List;

public record OrderPostDto(
        String receiverPhoneNumber,
        String receiverAddress,
        String receiverName,
        String note,
        List<OrderDetailPostDto> orderDetails
) {
}
