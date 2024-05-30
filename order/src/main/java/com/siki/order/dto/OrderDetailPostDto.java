package com.siki.order.dto;

public record OrderDetailPostDto (
        Long productId,
        int quantity,
        double price
) {
}
