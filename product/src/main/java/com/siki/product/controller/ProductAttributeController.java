package com.siki.product.controller;
import com.siki.product.dto.product.*;
import com.siki.product.service.ProductAttributeService;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ProductAttributeDto>> createProductAttributes(@Valid @RequestBody List<ProductAttributePostDto> productAttributePosts) {
        List<ProductAttributeDto> productAttributes = productAttributeService.save(productAttributePosts);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(productAttributes);
    }
}
