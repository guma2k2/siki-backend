package com.siki.review.service;

import com.siki.review.dto.PageableData;
import com.siki.review.dto.ReviewDto;
import com.siki.review.dto.ReviewPostDto;

import java.util.List;

public interface ReviewService {

    void createReviewForProduct(ReviewPostDto reviewPost);

    PageableData<ReviewDto> getByBaseProductId(Long baseProductId, Integer pageNum, int pageSize, Integer ratingStar, String sortDir, String sortField);
}
