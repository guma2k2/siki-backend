package com.siki.cart.service;

import com.siki.cart.dto.CartDto;

import java.util.List;

public interface CartService {
    void addToCart(Long productId);
    void removeCartByCartId(Long cartId);

    void removeCartOfLoggedUser();

    CartDto findByProductAndUser(Long productId);
    List<CartDto> getCartsForCustomer();
    void updateQuantity(Long CartId, int quantity);
    void updateSelected(Long cartId, boolean isSelected);

}
