package com.siki.product.service.impl;

import com.siki.product.dto.StoreDto;
import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.product.*;
import com.siki.product.exception.*;
import com.siki.product.model.*;
import com.siki.product.repository.*;
import com.siki.product.service.ProductAttributeSetService;
import com.siki.product.service.ProductService;
import com.siki.product.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductAttributeSetRepository productAttributeSetRepository;
    private final ProductImageRepository productImageRepository;
    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;
    private final ProductVariationRepository productVariationRepository;
    private final ProductAttributeSetService productAttributeSetService;
    private final ProductAttributeValueRepository productAttributeValueRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductAttributeSetRepository productAttributeSetRepository, ProductImageRepository productImageRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, ProductVariationRepository productVariationRepository, ProductAttributeSetService productAttributeSetService, ProductAttributeValueRepository productAttributeValueRepository) {
        this.productRepository = productRepository;
        this.productAttributeSetRepository = productAttributeSetRepository;
        this.productImageRepository = productImageRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productVariationRepository = productVariationRepository;
        this.productAttributeSetService = productAttributeSetService;
        this.productAttributeValueRepository = productAttributeValueRepository;
    }





    @Override
    public void create(List<ProductPostDto> productPostDtos) {
        productPostDtos.forEach(productDto -> {
            Product product = Product.builder()
                    .name(productDto.name())
                    .description(productDto.description())
                    .quantity(productDto.quantity())
                    .showIndividually(productDto.isShowIndividually())
                    .price(productDto.price())
                    .storeId(productDto.storeId())
                    .build();
            // Todo: get breadcrumb
            productRepository.saveAndFlush(product);
            setBrand(product, productDto.brandId());
            setProductAttributeSet(product, productDto.productAttributeSetId());
            setProductCategory(product, productDto.categoryId());
            setProductImages(product, productDto.productImageIds());
            setProductVariation(product, productDto.productOptionValueIds());
        });
    }

    private void setProductAttributeSet(Product product, Integer productAttributeSetId) {
        ProductAttributeSet productAttributeSet = productAttributeSetRepository.findById(productAttributeSetId).orElseThrow();
        product.setProductAttributeSet(productAttributeSet);

    }

    private void setProductVariation(Product product, List<Long> productAttributeValueIds) {
        List<ProductVariation> productVariations = new ArrayList<>();
        productAttributeValueIds.forEach(attributeValueId -> {
            ProductAttributeValue productAttributeValue = productAttributeValueRepository.findById(attributeValueId).orElseThrow();
            ProductVariation productVariation =
                    ProductVariation.builder()
                            .product(product)
                            .productAttributeValue(productAttributeValue)
                            .build();
            productVariations.add(productVariation);
        } );
        productVariationRepository.saveAllAndFlush(productVariations);
    }

    @Override
    public ProductDto getById(Long productId) {
        Product product = productRepository.findByIdCustom(productId)
                .orElseThrow(() -> new NotFoundException(Constants.ERROR_CODE.PRODUCT_NOT_FOUND, productId));
        product = productRepository.findByProduct(product).orElseThrow();
        BrandDto brandDto = BrandDto.fromModel(product.getBrand());
        StoreDto storeDto = null;
        List<ProductImageDto> productImageDtos = product.getProductImages().stream().map(productImage -> ProductImageDto.fromModel(productImage)).toList();
        ProductAttributeSetDto productAttributeSetDto = productAttributeSetService.findById(product.getProductAttributeSet().getId());
        List<ProductVariation> productVariations = productVariationRepository.findByProductId(productId);

        List<ProductAttributeValue> productAttributeValues = productVariations.stream().map(productVariation -> productVariation.getProductAttributeValue()).toList();
        List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(productAttributeValue -> ProductAttributeValueDto.fromModel(productAttributeValue)).toList();

        return ProductDto.fromModel(product, storeDto, brandDto, productImageDtos, productAttributeSetDto, productAttributeValueDtos);
    }

    private void setProductCategory(Product product, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        product.setCategory(category);
    }

    private String getBreadcrum(Integer categoryId) {
        String ans = "";
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        while(category.hasParent()) {
            ans = ans + "_" + category.getName();
            category = category.getParent();
        }
        return ans;
    }

    private void setProductImages(Product product, List<ProductImageDto> productImages) {
        List<ProductImage> productImageList = new ArrayList<>();
        if (productImages != null && productImages.size() > 0) {
            for(ProductImageDto productImage: productImages) {
                ProductImage newProductImage = ProductImage.builder()
                        .url(productImage.url())
                        .isDefault(productImage.isDefault())
                        .product(product)
                        .build();
                productImageList.add(newProductImage);
            }
            productImageRepository.saveAllAndFlush(productImageList);
            product.setProductImages(productImageList);
        }
    }

    private void setBrand(Product product, Integer brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new NotFoundException(Constants.ERROR_CODE.BRAND_NOT_FOUND, brandId));
        product.setBrand(brand);
    }
}
