package com.siki.product.dto.product;

import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.category.CategoryDto;
import com.siki.product.model.BaseProduct;

import java.util.List;

public record BaseProductDto (
        Long id,
        String name,
        String slug,
        String description,
        BrandDto brand,
        CategoryDto category,
        StoreDto store,
        List<ProductAttributeDto> productAttributes,
        List<ProductDto> productVariants,
        List<BaseProductGetListDto> relatedProducts,
        List<String> breadcrumb
) {
    public static BaseProductDto fromModel(BaseProduct baseProduct,
                                           StoreDto store,
                                           List<ProductAttributeDto> productAttributes,
                                           List<ProductDto> productVariants,
                                           List<String> breadcrumb,
                                           List<BaseProductGetListDto> relatedProducts
    ) {
        BrandDto brand = BrandDto.fromModel(baseProduct.getBrand());
        CategoryDto category = CategoryDto.fromModel(baseProduct.getCategory());
        return new BaseProductDto(
                baseProduct.getId(),
                baseProduct.getName(),
                baseProduct.getSlug(),
                baseProduct.getDescription(),
                brand,
                category,
                store,
                productAttributes,
                productVariants,
                relatedProducts,
                breadcrumb
        );
    }
}
