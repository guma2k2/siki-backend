package com.siki.product.service;

import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryPostDto;

import java.util.List;

public interface CategoryService {
    // Todo : get list category parent, get category by id, save category

    void create(CategoryPostDto categoryPostDto);
    CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId);

    void delete(Integer categoryId);

    List<CategoryDto> getAllCategoryParents();

    CategoryDto getById(Integer categoryId);
}
