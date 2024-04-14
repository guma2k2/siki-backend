package com.siki.product.service.impl;

import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryPostDto;
import com.siki.product.exception.DuplicatedException;
import com.siki.product.model.Category;
import com.siki.product.repository.CategoryRepository;
import com.siki.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void create(CategoryPostDto categoryPostDto) {
        Category categoryFound = categoryRepository.findByName(categoryPostDto.name()).orElseThrow();
        if (categoryFound != null)
            throw new DuplicatedException("Category name already exist");
        Category category = Category.builder()
                .name(categoryPostDto.name())
                .image(categoryPostDto.image())
                .description(categoryPostDto.description())
                .parent(categoryRepository.findById(categoryPostDto.categoryParentId()).orElseThrow())
                .build();
        if (categoryPostDto.categoryParentId() != null) {
            Category parent = categoryRepository.findById(categoryPostDto.categoryParentId()).orElseThrow();
            category.setParent(parent);
        }

        categoryRepository.save(category);
    }

    @Override
    public CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId) {
        Category categoryFound = categoryRepository.findByName(categoryPostDto.name()).orElseThrow();
        if (categoryFound != null)
            throw new DuplicatedException("Category name already exist");
        categoryFound.setName(categoryPostDto.name());
        categoryFound.setImage(categoryPostDto.image());
        categoryFound.setDescription(categoryPostDto.description());
        categoryRepository.saveAndFlush(categoryFound);
        return CategoryDto.fromModel(categoryFound);
    }

    @Override
    public void delete(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategoryParents() {
        List<Category> categoryList = categoryRepository.findCategoryParents();
        return categoryList.stream().map(category -> CategoryDto.fromModel(category)).toList();
    }

    @Override
    public CategoryDto getById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return CategoryDto.fromModel(category);
    }
}
