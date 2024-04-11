package com.siki.product.service.impl;

import com.siki.product.dto.category.CategoryDto;
import com.siki.product.dto.category.CategoryPostDto;
import com.siki.product.model.Category;
import com.siki.product.repository.CategoryRepository;
import com.siki.product.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public void create(CategoryPostDto categoryPostDto) {
        Category category = Category.builder()
                .name(categoryPostDto.name())
                .image(categoryPostDto.image())
                .description(categoryPostDto.description())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId) {
        Category categoryFound = categoryRepository.findById(categoryId).orElseThrow();
        categoryFound.setName(categoryPostDto.name());
        categoryFound.setImage(categoryPostDto.image());
        categoryFound.setDescription(categoryPostDto.description());
        categoryRepository.save(categoryFound);
        return CategoryDto.fromModel(categoryFound);
    }

    @Override
    public void delete(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategoryParents() {
        List<Category> categoryList = categoryRepository.findCategoryParents().orElseThrow();
        return categoryList.stream().map(c -> CategoryDto.fromModel(c)).toList();
    }

    @Override
    public CategoryDto getById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return CategoryDto.fromModel(category);
    }
}
