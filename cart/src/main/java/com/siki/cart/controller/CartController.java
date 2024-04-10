package com.siki.cart.controller;

import com.siki.cart.dto.CartDto;
import com.siki.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/storefront/add-to-cart/{productId}")
    public ResponseEntity<Void> addToCart(@PathVariable("productId") Long productId) {
        cartService.addToCart(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/storefront/{cartId}")
    public ResponseEntity<Void> removeCart(@PathVariable("cartId") Long cartId) {
        cartService.removeCartByCartId(cartId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/storefront/carts")
    public ResponseEntity<List<CartDto>> listCarts() {
        return ResponseEntity.ok().body(cartService.getCartsForCustomer());
    }

    @PutMapping("/storefront/quantity/{cartId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable("cartId") Long cartId,
                                               @RequestParam("quantity") int quantity) {
        cartService.updateQuantity(cartId, quantity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/storefront/selection/{cartId}")
    public ResponseEntity<Void> updateSelection(@PathVariable("cartId") Long cartId,
                                               @RequestParam("selection") boolean selection) {
        cartService.updateSelected(cartId, selection);
        return ResponseEntity.noContent().build();
    }

}
