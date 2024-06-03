package com.siki.product.service.impl;

import com.siki.product.dto.CustomerDto;
import com.siki.product.dto.PageableData;
import com.siki.product.dto.StoreDto;
import com.siki.product.dto.product.ProductVariantDto;
import com.siki.product.dto.review.ReviewDto;
import com.siki.product.dto.review.ReviewPostDto;
import com.siki.product.model.*;
import com.siki.product.repository.ProductAttributeValueRepository;
import com.siki.product.repository.ProductRepository;
import com.siki.product.repository.ProductVariationRepository;
import com.siki.product.repository.ReviewRepository;
import com.siki.product.service.ReviewService;
import com.siki.product.service.client.CustomerFeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final CustomerFeignClient customerFeignClient;

    private final ProductRepository productRepository;

    private final ProductAttributeValueRepository productAttributeValueRepository;

    private final ProductVariationRepository productVariationRepository;

    private final static String sortField = "createdAt";

    public ReviewServiceImpl(ReviewRepository reviewRepository, CustomerFeignClient customerFeignClient, ProductRepository productRepository, ProductAttributeValueRepository productAttributeValueRepository, ProductVariationRepository productVariationRepository) {
        this.reviewRepository = reviewRepository;
        this.customerFeignClient = customerFeignClient;
        this.productRepository = productRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
        this.productVariationRepository = productVariationRepository;
    }

    @Override
    public void createReviewForProduct(ReviewPostDto reviewPost) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Product product = productRepository.findById(reviewPost.productId()).orElseThrow();
        Review review = Review.builder()
                .content(reviewPost.content())
                .ratingStar(reviewPost.ratingStar())
                .product(product)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepository.save(review);
    }

    @Override
    public PageableData<ReviewDto> getByBaseProductId(
            String baseProductSlug,
            Integer pageNum,
            int pageSize,
            List<Integer> ratingStars,
            String sortDir
    ) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Review> reviewPage = null;
        if (ratingStars != null && !ratingStars.isEmpty()) {
            reviewPage = reviewRepository.findByRatingStar(ratingStars, baseProductSlug, pageable);
        }else {
            reviewPage = reviewRepository.findByProductSlug(baseProductSlug, pageable);
        }
        List<Review> reviews = reviewPage.getContent();

        int totalPages = reviewPage.getTotalPages();
        long totalElements = reviewPage.getTotalElements();

        List<ReviewDto> reviewDtos = reviews.stream().map(review -> {
            String customerId = review.getCustomerId();
            Long productId = review.getProduct().getId();
            String variant = getVariantById(productId);
            CustomerDto customerDto = customerFeignClient.getCustomerById(customerId).getBody();
            return ReviewDto.fromModel(review, customerDto, variant);
        }).toList();

        return new PageableData<>(pageNum, pageSize, totalElements, totalPages, reviewDtos);
    }

    private String getVariantById(Long productId) {
        StringBuilder variant = new StringBuilder();
        List<ProductVariation> productVariations = productVariationRepository.findByProductId(productId);
        List<ProductAttributeValue> productAttributeValues = productVariations.stream().map(productVariation -> {
            Long productAttributeValueId = productVariation.getProductAttributeValue().getId();
            ProductAttributeValue productAttributeValue = productAttributeValueRepository.findById(productAttributeValueId).orElseThrow();
            return productAttributeValue;
        }).toList();

        for (int start = 0; start < productAttributeValues.size(); start++) {
            if (start > 0) {
                variant.append(" . ");
            }
            ProductAttributeValue productAttributeValue = productAttributeValues.get(start);
            ProductAttribute productAttribute = productAttributeValue.getProductAttribute();
            variant.append((productAttribute.getName()).concat(" : ")).append(productAttributeValue.getValue());
        }
        return variant.toString();
    }


}
