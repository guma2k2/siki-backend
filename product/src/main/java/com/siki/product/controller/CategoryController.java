package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryGetDto;
import com.siki.product.dto.category.CategoryListDto;
import com.siki.product.dto.category.CategoryPostDto;
import com.siki.product.service.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/backoffice/category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        categoryService.create(categoryPostDto);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/backoffice/category/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated", content = @Content(schema = @Schema(implementation = CategoryPostDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryPostDto categoryPostDto, @PathVariable("id") Integer id) {
        CategoryDto categoryDto = categoryService.update(categoryPostDto, id);
        return ResponseEntity.ok().body(categoryDto);
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CategoryListDto[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<List<CategoryListDto>> listCategoryToListDto() {
        List<CategoryListDto> categoryListDtos = categoryService.listAllToListDto();
        return ResponseEntity.ok().body(categoryListDtos);
    }


    @GetMapping("/category/{name}")
    public ResponseEntity<CategoryGetDto> listCategoryByName(
            @PathVariable("name") String name
    ) {
        return ResponseEntity.ok().body(categoryService.listByName(name));
    }

    @DeleteMapping("/backoffice/category/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
