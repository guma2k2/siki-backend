package com.siki.product.service;

import com.siki.product.dto.PageableData;
import com.siki.product.dto.review.ReviewDto;
import com.siki.product.dto.review.ReviewPostDto;

import java.util.List;

public interface ReviewService {

    void createReviewForProduct(ReviewPostDto reviewPost);

    PageableData<ReviewDto> getByBaseProductId(String baseProductSlug, Integer pageNum, int pageSize, List<Integer> ratingStars, String sortDir);
}
