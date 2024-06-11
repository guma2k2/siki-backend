package com.siki.product.dto.category;

import com.siki.product.model.Category;

public record CategoryAdminDto (
    Integer id,
    String name,
    String description,
    String image,
    Integer categoryParentId
) {
    public static CategoryAdminDto fromModel(Category category) {
        if (category.getParent() == null) {
            return new CategoryAdminDto(category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.getImage(),
                    null);
        }
        return new CategoryAdminDto(category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImage(),
                category.getParent().getId());
    }
}
