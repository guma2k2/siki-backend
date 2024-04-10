package com.siki.review.service.impl;

import com.siki.review.dto.*;
import com.siki.review.model.Review;
import com.siki.review.repository.ReviewRepository;
import com.siki.review.service.ReviewService;
import com.siki.review.service.client.ProductFeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ProductFeignClient productFeignClient;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductFeignClient productFeignClient) {
        this.reviewRepository = reviewRepository;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public void createReviewForProduct(ReviewPostDto reviewPost) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Review review = Review.builder()
                .content(reviewPost.content())
                .ratingStar(reviewPost.ratingStar())
                .productId(reviewPost.productId())
                .baseProductId(reviewPost.baseProductId())
                .customerId(customerId)
                .build();
        reviewRepository.save(review);
    }

    @Override
    public PageableData<ReviewDto> getByBaseProductId(Long baseProductId,
                                                      Integer pageNum,
                                                      int pageSize,
                                                      Integer ratingStar,
                                                      String sortDir,
                                                      String sortField
    ) {

        Sort sort = Sort.by(sortField);
        sort = sortDir == "desc" ? sort.descending() : sort.ascending()    ;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Review> reviewPage = null;
        if (ratingStar != null) {
            reviewPage = reviewRepository.findByRatingStar(ratingStar, baseProductId, pageable);
        }else {
            reviewPage = reviewRepository.findByProductId(baseProductId, pageable);
        }
        List<Review> reviews = reviewPage.getContent();

        int totalPages = reviewPage.getTotalPages();
        long totalElements = reviewPage.getTotalElements();

        List<ReviewDto> reviewDtos = reviews.stream().map(review -> {
            String customerId = review.getCustomerId();
            Long productId = review.getProductId();
            ProductVariantDto product = productFeignClient.getByProductId(productId).getBody();
            // Todo: get customer by id
            CustomerDto customerDto = null;
            return ReviewDto.fromModel(review, customerDto, product);
        }).toList();

        return new PageableData<>(pageNum, pageSize, totalElements, totalPages, reviewDtos);
    }


}
