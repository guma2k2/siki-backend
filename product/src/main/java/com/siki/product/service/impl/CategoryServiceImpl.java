package com.siki.product.service.impl;

import com.siki.product.dto.category.*;
import com.siki.product.exception.DuplicatedException;
import com.siki.product.model.BaseProduct;
import com.siki.product.model.Brand;
import com.siki.product.model.Category;
import com.siki.product.repository.BaseProductRepository;
import com.siki.product.repository.CategoryRepository;
import com.siki.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BaseProductRepository baseProductRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BaseProductRepository baseProductRepository) {
        this.categoryRepository = categoryRepository;
        this.baseProductRepository = baseProductRepository;
    }

    @Override
    public void create(CategoryPostDto categoryPostDto) {
        Optional<Category> categoryFound = categoryRepository.findByName(categoryPostDto.name());
        if (categoryFound.isPresent()){
            throw new DuplicatedException("Category name already exist");
        }

        Category category = Category.builder()
                .name(categoryPostDto.name())
                .image(categoryPostDto.image())
                .description(categoryPostDto.description())
                .status(true)
                .build();

        if (categoryPostDto.categoryParentId() != null) {
            Category parent = categoryRepository.findById(categoryPostDto.categoryParentId()).orElseThrow();
            category.setParent(parent);
        }

        categoryRepository.save(category);
    }

    @Override
    public CategoryDto update(CategoryPostDto categoryPostDto, Integer categoryId) {
        Boolean isCategoryFound = categoryRepository.findByIdAndName(categoryId, categoryPostDto.name()).isPresent();
        if (isCategoryFound)
            throw new DuplicatedException("Category name already exist");
        Category categoryFound = categoryRepository.findById(categoryId).orElseThrow();
        categoryFound.setName(categoryPostDto.name());
        categoryFound.setImage(categoryPostDto.image());
        categoryFound.setDescription(categoryPostDto.description());
        if (categoryPostDto.categoryParentId() != null) {
            Category parent = categoryRepository.findById(categoryPostDto.categoryParentId()).orElseThrow();
            categoryFound.setParent(parent);
        } else {
            categoryFound.setParent(null);
        }
        categoryRepository.saveAndFlush(categoryFound);
        return CategoryDto.fromModel(categoryFound);
    }

    @Override
    public boolean delete(Integer categoryId) {
        List<Category> categoryChildren = categoryRepository.findByParentId(categoryId);
        if (baseProductRepository.findByCategoryId(categoryId).isEmpty() && categoryChildren.isEmpty()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            categoryRepository.delete(category);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<CategoryDto> getAllCategoryParents() {
        List<Category> categoryList = categoryRepository.findCategoryParents();
        return categoryList.stream().map(CategoryDto::fromModel).toList();
    }

    @Override
    public List<CategoryAdminDto> getAllCategoryDto() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(CategoryAdminDto::fromModel).toList();
    }

    @Override
    public CategoryDto getById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return CategoryDto.fromModel(category);
    }

    @Override
    public List<CategoryListDto> listAllToListDto() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryListDto> categoryListDto = categoryList.stream().map(c -> {
            String parentName;
            if (c.getParent() == null) {
                parentName = null;
            } else {
                parentName = c.getParent().getName();
            }
            List<Category> childrenList = c.getChildrenList();
            return CategoryListDto.fromModel(c, parentName, childrenList);
        }).toList();
        return categoryListDto;
    }


    @Override
    public CategoryGetDto listByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow();

        List<Category> categoryList = category.getChildrenList();
        List<CategoryDto> categoryDtos = categoryList.stream().map(CategoryDto::fromModel).toList();
        List<BaseProduct> baseProductList = category.getProductList();
        List<Brand> brands = baseProductList.stream().map(baseProduct -> baseProduct.getBrand()).distinct().toList();
        List<String> brandNames = brands.stream().map(Brand::getName).toList();
        return new CategoryGetDto(categoryDtos, brandNames);
    }


}
