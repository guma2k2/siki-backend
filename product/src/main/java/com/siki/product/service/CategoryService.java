package com.siki.product.service;

import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryGetDto;
import com.siki.product.dto.category.CategoryListDto;
import com.siki.product.dto.category.CategoryPostDto;

import java.util.List;

public interface CategoryService {

    void create(CategoryPostDto categoryPostDto);
    
    CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId);

    void delete(Integer categoryId);

    List<CategoryDto> getAllCategoryParents();

    CategoryDto getById(Integer categoryId);

    List<CategoryListDto> listAllToListDto();

    CategoryGetDto listByName(String categoryName);
}
