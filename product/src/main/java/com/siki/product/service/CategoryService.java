package com.siki.product.service;

import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryPostDto;

import java.util.List;

public interface CategoryService {
    // Todo1 : get category by name return category and 10 these products
    // Todo2 : when get a category then, return a list of category childrens, and these products (pageable data) 

    void create(CategoryPostDto categoryPostDto);
    
    CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId);

    void delete(Integer categoryId);

    List<CategoryDto> getAllCategoryParents();

    CategoryDto getById(Integer categoryId);
}
