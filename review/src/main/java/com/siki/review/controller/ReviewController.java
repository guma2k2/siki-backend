package com.siki.review.controller;

import com.siki.review.dto.PageableData;
import com.siki.review.dto.ReviewDto;
import com.siki.review.dto.ReviewPostDto;
import com.siki.review.service.ReviewService;
import com.siki.review.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/storefront")
    public ResponseEntity<Void> createReview(@Valid @RequestBody ReviewPostDto reviewPostDto){
        reviewService.createReviewForProduct(reviewPostDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/storefront/baseProduct/{id}")
    public ResponseEntity<PageableData<ReviewDto>> getByBaseProductId(
            @PathVariable("id") Long baseProductId,

            @RequestParam(value = "pageNum", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_NUMBER) int pageNum,

            @RequestParam(value = "pageSize", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_SIZE) int pageSize,

            @RequestParam(value = "ratingStar", required = false) int ratingStar,

            @RequestParam(value = "sortDir", required = false) String sortDir,

            @RequestParam(value = "sortField", required = false) String sortField
    ) {
        return ResponseEntity.ok().body(reviewService.getByBaseProductId(baseProductId, pageNum, pageSize, ratingStar,sortDir,sortField));
    }


}
