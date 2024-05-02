package com.siki.product.dto.category;

import com.siki.product.model.Category;

import java.util.ArrayList;
import java.util.List;

public record CategoryListDto(Integer id, String image, String parentName, List<String> childrenName) {
    public static CategoryListDto fromModel(Category category, String parentName, List<Category> childrens) {
        List<String> names = new ArrayList<>();
        for (Category c : childrens) {
            names.add(c.getName());
        }
        return new CategoryListDto(category.getId(), category.getImage(), parentName, names);
    }
}
