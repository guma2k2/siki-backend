package com.siki.product.dto.review;

import com.siki.product.dto.CustomerDto;
import com.siki.product.dto.product.ProductVariantDto;
import com.siki.product.model.Review;

public record ReviewDto (
        Long id,
        String content,
        CustomerDto customer,
        ProductVariantDto product,
        String created_at,
        String updated_at
) {

    public static ReviewDto fromModel(Review review,
                                      CustomerDto customer,
                                      ProductVariantDto product
                                      ) {
        return new ReviewDto(
                review.getId(), review.getContent(), customer, product, review.getCreatedAt().toString(), review.getUpdatedAt().toString()
        );
    }
}
