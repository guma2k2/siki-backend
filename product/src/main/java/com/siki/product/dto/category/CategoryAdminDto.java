package com.siki.product.dto.category;

import com.siki.product.model.Category;

public record CategoryAdminDto (
    Integer id,
    String name,
    String description,
    String image
) {
    public static CategoryAdminDto fromModel(Category category) {
        return new CategoryAdminDto(category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImage());
    }
}
