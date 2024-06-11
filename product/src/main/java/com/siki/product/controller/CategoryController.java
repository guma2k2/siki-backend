package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.category.*;
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
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/backoffice/category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        categoryService.create(categoryPostDto);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CategoryListDto[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<List<CategoryListDto>> listCategoryToListDto() {
        List<CategoryListDto> categoryListDtos = categoryService.listAllToListDto();
        return ResponseEntity.ok().body(categoryListDtos);
    }


    @GetMapping("/category/parents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CategoryListDto[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<List<CategoryDto>> getCategoryParents() {
        List<CategoryDto> allCategoryParents = categoryService.getAllCategoryParents();
        return ResponseEntity.ok().body(allCategoryParents);
    }

    @GetMapping("/category/all")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CategoryListDto[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<List<CategoryAdminDto>> getAllCategory() {
        List<CategoryAdminDto> allCategoryDto = categoryService.getAllCategoryDto();
        return ResponseEntity.ok().body(allCategoryDto);
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<CategoryGetDto> listCategoryByName(
            @PathVariable("name") String name
    ) {
        return ResponseEntity.ok().body(categoryService.listByName(name));
    }

    @GetMapping("/category/name/{id}")
    public ResponseEntity<CategoryDto> findByIdReturnName(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok().body(categoryService.getById(id));
    }

    @DeleteMapping("/backoffice/category/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
