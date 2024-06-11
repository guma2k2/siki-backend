package com.siki.product.controller;

import com.siki.product.dto.ErrorDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.brand.BrandPostDto;
import com.siki.product.dto.category.CategoryAdminDto;
import com.siki.product.dto.category.CategoryListDto;
import com.siki.product.model.Brand;
import com.siki.product.service.BrandService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/backoffice/brand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> createBrand(@Valid @RequestBody BrandPostDto brandPostDto) {
        brandService.create(brandPostDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/backoffice/brand/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated", content = @Content(schema = @Schema(implementation = BrandPostDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<BrandDto> updateBrand(@Valid @RequestBody BrandPostDto brandPostDto, @PathVariable("id") Integer id) {
        BrandDto brandDto = brandService.update(brandPostDto, id);
        return ResponseEntity.ok().body(brandDto);
    }

    @DeleteMapping("/backoffice/brand/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> deletedBrand(@PathVariable Integer id) {
        if (brandService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/brand/all")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BrandDto[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> brandDtos = brandService.getAllBrands();
        return ResponseEntity.ok().body(brandDtos);
    }

}
