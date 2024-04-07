package com.siki.product.dto.category;

import com.siki.product.model.Category;

public record CategoryDto (
        Integer id,
        String name,
        String image,
        String description
) {
    public static CategoryDto fromModel(Category category) {
        return new CategoryDto(category.getId(),
                category.getName(),
                category.getImage(),
                category.getDescription());
    }
}
