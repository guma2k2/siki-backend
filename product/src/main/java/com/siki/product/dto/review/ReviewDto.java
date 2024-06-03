package com.siki.product.dto.review;

import com.siki.product.dto.CustomerDto;
import com.siki.product.dto.product.ProductVariantDto;
import com.siki.product.model.Review;

public record ReviewDto (
        Long id,
        String content,
        int rating,
        CustomerDto customer,
        String variant,
        String created_at,
        String updated_at
) {

    public static ReviewDto fromModel(Review review,
                                      CustomerDto customer,
                                      String variant
                                      ) {
        String updatedAt = review.getUpdatedAt() != null ? review.getUpdatedAt().toString() : "";
        return new ReviewDto(
                review.getId(), review.getContent(), review.getRatingStar(), customer, variant, review.getCreatedAt().toString(), updatedAt
        );
    }
}
