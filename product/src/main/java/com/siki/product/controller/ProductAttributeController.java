package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributePostDto;
import com.siki.product.dto.product.ProductDto;
import com.siki.product.dto.product.ProductPostDto;
import com.siki.product.service.ProductAttributeService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;

    public ProductAttributeController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @PostMapping("/backoffice/product-attributes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProductAttributePostDto[].class))),
    })
    public ResponseEntity<List<ProductAttributeDto>> createProductAttributes(@Valid @RequestBody List<ProductAttributePostDto> productAttributePostDtoList) {
        List<ProductAttributeDto> productAttributeDtos = productAttributeService.save(productAttributePostDtoList);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(productAttributeDtos);

    }
}
