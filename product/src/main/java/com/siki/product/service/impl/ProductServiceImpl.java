package com.siki.product.service.impl;

import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.product.*;
import com.siki.product.exception.*;
import com.siki.product.model.*;
import com.siki.product.repository.*;
import com.siki.product.service.ProductService;
import com.siki.product.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long create(ProductPostDto productPostDto) {
        if (checkExistedProductByName(productPostDto.name(), null)) {
            throw new DuplicatedException(Constants.ERROR_CODE.PRODUCT_NAME_IS_EXISTED, productPostDto.name());
        }
        Product product = Product.builder()
                .name(productPostDto.name())
                .description(productPostDto.description())
                .quantity(productPostDto.quantity())
                .status(productPostDto.status())
                .storeId(productPostDto.storeId())
                .price(productPostDto.price())
                .build();

        Product savedProduct = productRepository.saveAndFlush(product);
        setBrand(product, productPostDto.brandId());
        setProductImages(product, productPostDto.productImages());
        setProductCategories(product, productPostDto.productCategories());
        return savedProduct.getId();
    }

    private void setProductCategories(Product product, List<Integer> productCategories) {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        for(Integer categoryId : productCategories){
            Category category = categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException(Constants.ERROR_CODE.CATEGORY_NOT_FOUND, categoryId));
            ProductCategory productCategory = ProductCategory.builder()
                    .product(product)
                    .category(category)
                    .build();
            productCategoryList.add(productCategory);
        }
        product.setProductCategories(productCategoryList);
    }

    private void setProductImages(Product product, List<ProductImageDto> productImages) {
        List<ProductImage> productImageList = new ArrayList<>();
        for(ProductImageDto productImage: productImages) {
            ProductImage newProductImage = ProductImage.builder()
                    .urlPath(productImage.urlPath())
                    .isDefault(productImage.isDefault())
                    .product(product)
                    .build();
            productImageList.add(newProductImage);
        }
        productImageRepository.saveAllAndFlush(productImageList);
        product.setProductImages(productImageList);
    }

    private void setBrand(Product product, Integer brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException(Constants.ERROR_CODE.BRAND_NOT_FOUND, brandId));
        product.setBrand(brand);
    }

    private boolean checkExistedProductByName(String name, Long id) {
        return productRepository.findExistedName(name, id) != null;
    }

    @Override
    public ProductDto getById(Long productId) {
        Product product = productRepository.findByIdCustom(productId)
                .orElseThrow(() -> new NotFoundException(Constants.ERROR_CODE.PRODUCT_NOT_FOUND, productId));
        BrandDto brandDto = BrandDto.fromModel(product.getBrand());
        StoreDto storeDto = null    ;
        List<ProductImageDto> productImageDtos = product.getProductImages().stream().map(productImage -> ProductImageDto.fromModel(productImage)).toList();

        // get store from user service
        return ProductDto.fromModel(product, storeDto, brandDto, productImageDtos);
    }
}
