package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.PageableData;
import com.siki.product.dto.product.*;
import com.siki.product.service.ProductService;
import com.siki.product.service.client.MediaFeignClient;
import com.siki.product.utils.Constants;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    /*
        storefront: customer
        backoffice: admin
    */

    @GetMapping(value = {"/backoffice/products/{id}", "/storefront/products/{id}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<BaseProductDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }
    @GetMapping(value =  "/storefront/product/{slug}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<BaseProductDto> getProductById(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(productService.getBySlug(slug));
    }

    @GetMapping(value =  "/storefront/category/{categoryId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<List<BaseProductGetListDto>> getProductById(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(productService.getByCategory(categoryId));
    }

    @GetMapping("/storefront/products-variant/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<ProductVariantDto> getByProductId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findProductVariantById(id));
    }
    @GetMapping("/storefront/products")
    public ResponseEntity<PageableData<BaseProductGetListDto>> getProductsByMultiQuery(
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "brandNames", defaultValue = "") String[] brandNames,
            @RequestParam(value = "pageNum", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortDir", defaultValue = Constants.PageableConstant.DEFAULT_SORT_DIR, required = false) String sortDir,
            @RequestParam(value = "sortField", defaultValue = Constants.PageableConstant.DEFAULT_SORT_FIELD, required = false) String sortField,
            @RequestParam(value = "startPrice", required = false) Double startPrice,
            @RequestParam(value = "endPrice", required = false) Double endPrice,
            @RequestParam(value = "ratingStar", required = false) int[] ratingStars
    ) {
        return ResponseEntity.ok().body(productService.getProductByMultiQuery(categoryName, brandNames, pageNum, pageSize, sortDir, sortField, startPrice, endPrice, ratingStars));
    }

    @PostMapping("/backoffice/products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<Void> createProduct(@Valid @RequestBody BaseProductPostDto baseProductPostDto) {
        productService.create(baseProductPostDto);
        return ResponseEntity.noContent().build();
    }

    // Todo : update quantity, get relatedProduct, get product multi query
}
