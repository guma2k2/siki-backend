package com.siki.cart.service;

import com.siki.cart.dto.CartDto;
import com.siki.cart.dto.ProductVariantDto;
import com.siki.cart.model.Cart;
import com.siki.cart.repository.CartRepository;
import com.siki.cart.service.client.ProductFeignClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductFeignClient productFeignClient;

    public CartServiceImpl(CartRepository cartRepository, ProductFeignClient productFeignClient) {
        this.cartRepository = cartRepository;
        this.productFeignClient = productFeignClient;
    }

    @Override
    @Transactional
    public void addToCart(Long productId) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cart> cart = cartRepository.findByProductAndUser(productId, customerId);
        if (cart.isPresent()) {
            int currentQuantity = cart.get().getQuantity();
            cartRepository.updateQuantity(cart.get().getId(), currentQuantity + 1);
        }else {
            Cart newCart = Cart.builder()
                    .quantity(1)
                    .productId(productId)
                    .userId(customerId)
                    .selected(false)
                    .build();
            cartRepository.save(newCart);
        }
    }

    @Override
    @Transactional
    public void removeCartByCartId(Long cartId) {
        cartRepository.deleteCart(cartId);
    }

    @Override
    @Transactional
    public void removeCartOfLoggedUser() {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartRepository.deleteCartByUser(customerId);
    }

    @Override
    public CartDto findByProductAndUser(Long productId) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = cartRepository.findByProductAndUser(productId, customerId).orElseThrow();
        ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
        return CartDto.fromModel(cart, productVariantDto);
    }

    @Override
    public List<CartDto> getCartsForCustomer(String customerId) {
        List<Cart> carts = cartRepository.findByUserId(customerId);
        List<CartDto> cartDtos = carts.stream().map(cart -> {
            Long productId = cart.getProductId();
            ProductVariantDto productVariantDto = productFeignClient.getByProductId(productId).getBody();
            return CartDto.fromModel(cart, productVariantDto);
        }).toList();
        return cartDtos;
    }

    @Override
    @Transactional
    public void updateQuantity(Long CartId, int quantity) {
        cartRepository.updateQuantity(CartId, quantity);
    }

    @Override
    @Transactional
    public void updateSelected(Long cartId, boolean isSelected) {
        cartRepository.updateSelected(cartId, isSelected);
    }

    @Override
    @Transactional
    public void updateSelectedOfUser(boolean selection) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartRepository.updateSelected(customerId, selection);
    }
}
