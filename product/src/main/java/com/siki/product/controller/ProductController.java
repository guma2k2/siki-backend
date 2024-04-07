package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.product.BaseProductDto;
import com.siki.product.dto.product.BaseProductPostDto;
import com.siki.product.dto.product.ProductDto;
import com.siki.product.dto.product.ProductPostDto;
import com.siki.product.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/backoffice/products/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<BaseProductDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping("/backoffice/products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))})
    public ResponseEntity<Void> createProduct(@Valid @RequestBody BaseProductPostDto baseProductPostDto) {
        productService.create(baseProductPostDto);
        return ResponseEntity.noContent().build();
    }

}
