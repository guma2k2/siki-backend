package com.siki.cart.dto;

import com.siki.cart.model.Cart;

public record CartDto (
        Long id,
        ProductVariantDto product,
        int quantity,
        boolean isSelected
) {

    public static CartDto fromModel(Cart cart, ProductVariantDto product) {
        return new CartDto(cart.getId(), product, cart.getQuantity(), cart.isSelected());
    }
}
