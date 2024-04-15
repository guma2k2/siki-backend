package com.siki.product.service.impl;

import com.siki.product.dto.CustomerDto;
import com.siki.product.dto.PageableData;
import com.siki.product.dto.StoreDto;
import com.siki.product.dto.product.ProductVariantDto;
import com.siki.product.dto.review.ReviewDto;
import com.siki.product.dto.review.ReviewPostDto;
import com.siki.product.model.Product;
import com.siki.product.model.ProductAttributeValue;
import com.siki.product.model.ProductVariation;
import com.siki.product.model.Review;
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

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final CustomerFeignClient customerFeignClient;

    private final ProductRepository productRepository;

    private final ProductVariationRepository productVariationRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CustomerFeignClient customerFeignClient, ProductRepository productRepository, ProductVariationRepository productVariationRepository) {
        this.reviewRepository = reviewRepository;
        this.customerFeignClient = customerFeignClient;
        this.productRepository = productRepository;
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
                .build();
        reviewRepository.save(review);
    }

    @Override
    public PageableData<ReviewDto> getByBaseProductId(
            Long baseProductId,
            Integer pageNum,
            int pageSize,
            Integer ratingStar,
            String sortDir,
            String sortField
    ) {

        Sort sort = Sort.by(sortField);
        sort = sortDir == "desc" ? sort.descending() : sort.ascending()    ;
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
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
            Long productId = review.getProduct().getId();
            ProductVariantDto product = findProductVariantById(productId);
            CustomerDto customerDto = customerFeignClient.getCustomerById(customerId).getBody();
            return ReviewDto.fromModel(review, customerDto, product);
        }).toList();

        return new PageableData<>(pageNum, pageSize, totalElements, totalPages, reviewDtos);
    }

    private ProductVariantDto findProductVariantById(Long productId) {
        Product product = productRepository.findByIdCustom(productId).orElseThrow();
        StoreDto store = null;
        List<ProductVariation> productVariations = productVariationRepository.findByProductId(productId);
        List<ProductAttributeValue> productAttributeValues = productVariations.stream().map(ProductVariation::getProductAttributeValue).toList();
        List<String> values = productAttributeValues.stream().map(productAttributeValue -> productAttributeValue.getValue()).toList();
        return ProductVariantDto.fromModel(product, values, store);
    }


}
