package com.siki.review.dto;

import com.siki.review.model.Review;

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
