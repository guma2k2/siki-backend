package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.category.CategoryGetDto;
import com.siki.product.dto.category.CategoryPostDto;
import com.siki.product.service.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created", content = @Content(schema = @Schema(implementation = CategoryPostDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        categoryService.create(categoryPostDto);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CategoryGetDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<CategoryGetDto> listCategory(@Valid @RequestParam String categoryName) {
        // list all but return no content => ???
        CategoryGetDto categoryGetDto = categoryService.listByName(categoryName);
        return ResponseEntity.ok().body(categoryGetDto);
    }
}
