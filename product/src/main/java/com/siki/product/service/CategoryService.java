package com.siki.product.service;

import com.siki.product.dto.category.*;

import java.util.List;

public interface CategoryService {

    void create(CategoryPostDto categoryPostDto);
    
    CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId);

    boolean delete(Integer categoryId);

    List<CategoryDto> getAllCategoryParents();
    List<CategoryAdminDto> getAllCategoryDto();

    CategoryDto getById(Integer categoryId);

    List<CategoryListDto> listAllToListDto();

    CategoryGetDto listByName(String categoryName);
}
