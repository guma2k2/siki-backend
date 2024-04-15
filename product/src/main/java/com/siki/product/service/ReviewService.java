package com.siki.product.service;

import com.siki.product.dto.PageableData;
import com.siki.product.dto.review.ReviewDto;
import com.siki.product.dto.review.ReviewPostDto;

public interface ReviewService {

    void createReviewForProduct(ReviewPostDto reviewPost);

    PageableData<ReviewDto> getByBaseProductId(Long baseProductId, Integer pageNum, int pageSize, Integer ratingStar, String sortDir, String sortField);
}
