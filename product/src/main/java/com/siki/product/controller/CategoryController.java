package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.category.CategoryPostDto;
import com.siki.product.service.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/create")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created", content = @Content(schema = @Schema(implementation = CategoryPostDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        categoryService.create(categoryPostDto);
        return  ResponseEntity.noContent().build();
    }
}
