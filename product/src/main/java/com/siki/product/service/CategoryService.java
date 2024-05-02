package com.siki.product.service;

import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryGetDto;
import com.siki.product.dto.category.CategoryListDto;
import com.siki.product.dto.category.CategoryPostDto;

import java.util.List;

public interface CategoryService {
    // Todo 1 : get category by name return category and 10 these products
    // Todo 2 : when get a category then, return a list of category childrens, and these products (PageableData)

    void create(CategoryPostDto categoryPostDto);
    
    CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId);

    void delete(Integer categoryId);

    List<CategoryDto> getAllCategoryParents();

    CategoryDto getById(Integer categoryId);

    //CategoryGetDto listAllByName(String categoryName);
    List<CategoryListDto> listAllToListDto();

    CategoryGetDto listByName(String categoryName);
}
