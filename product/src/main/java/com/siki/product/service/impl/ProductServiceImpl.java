package com.siki.product.service.impl;

import com.siki.product.dto.PageableData;
import com.siki.product.dto.StoreDto;
import com.siki.product.dto.product.*;
import com.siki.product.exception.*;
import com.siki.product.model.*;
import com.siki.product.repository.*;
import com.siki.product.service.ProductService;
import com.siki.product.service.client.MediaFeignClient;
import com.siki.product.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final BrandRepository brandRepository;
    private final BaseProductRepository baseProductRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariationRepository productVariationRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductAttributeRepository productAttributeRepository;

    private final MediaFeignClient mediaFeignClient;

    public ProductServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository, BrandRepository brandRepository, BaseProductRepository baseProductRepository, CategoryRepository categoryRepository, ProductVariationRepository productVariationRepository, ProductAttributeValueRepository productAttributeValueRepository, ProductAttributeRepository productAttributeRepository, MediaFeignClient mediaFeignClient) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.brandRepository = brandRepository;
        this.baseProductRepository = baseProductRepository;
        this.categoryRepository = categoryRepository;
        this.productVariationRepository = productVariationRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.mediaFeignClient = mediaFeignClient;
    }


    @Override
    @Transactional
    public void create(BaseProductPostDto baseProductPostDto) {
        // Todo : check exist slug
        BaseProduct baseProduct = BaseProduct.builder()
                .name(baseProductPostDto.name())
                .slug(baseProductPostDto.slug())
                .description(baseProductPostDto.description())
                .storeId(baseProductPostDto.storeId())
                .status(false)
                .build();
        baseProductRepository.saveAndFlush(baseProduct);
        setProductAttributes(baseProduct, baseProductPostDto.attributeIds());
        setBrand(baseProduct, baseProductPostDto.brandId());
        setProductCategory(baseProduct, baseProductPostDto.categoryId());
        setProductVariants(baseProduct, baseProductPostDto.productPosts());
    }

    @Override
    public BaseProductDto getById(Long baseProductId) {
        BaseProduct baseProduct = baseProductRepository.findByIdCustom(baseProductId).orElseThrow();
        List<ProductAttribute> productAttributes = productAttributeRepository.findByBaseProductId(baseProductId);
        List<ProductAttributeDto> productAttributeDtos =
                productAttributes.stream().map(productAttribute -> {
                    List<ProductAttributeValue> productAttributeValues = productAttribute.getProductAttributeValues();
                    List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(productAttributeValue -> {
                        String image = "";
                        if (productAttributeValue.getImage() != "" && productAttributeValue.getImage() != null) {
                            image = mediaFeignClient.getUrlById(productAttributeValue.getImage()).getBody();
                        }
                        return ProductAttributeValueDto.fromModel(productAttributeValue, image);
                    }).toList();
                    return ProductAttributeDto.fromModel(productAttribute, productAttributeValueDtos);
                }).toList();
        List<ProductDto> productVariants = getProductVariantsById(baseProductId);
        List<String> breadcrumb = getBreadcrumb(baseProduct.getCategory().getId(), baseProduct.getName());
        StoreDto storeDto = null;
        return BaseProductDto.fromModel(baseProduct, storeDto,productAttributeDtos  , productVariants, breadcrumb);
    }

    @Override
    public ProductVariantDto findProductVariantById(Long productId) {
        Product product = productRepository.findByIdCustom(productId).orElseThrow();
        StoreDto store = null;
        List<ProductVariation> productVariations = productVariationRepository.findByProductId(productId);
        List<ProductAttributeValue> productAttributeValues = productVariations.stream().map(ProductVariation::getProductAttributeValue).toList();
        List<String> values = productAttributeValues.stream().map(productAttributeValue -> productAttributeValue.getValue()).toList();
        return ProductVariantDto.fromModel(product, values, store);
    }

    @Override
    public PageableData<BaseProductGetListDto> getProductByMultiQuery(String categoryName,
                                                                      String[] brandNames,
                                                                      int pageNum,
                                                                      int pageSize,
                                                                      String sortDir,
                                                                      String sortField,
                                                                      Double startPrice,
                                                                      Double endPrice,
                                                                      int ratingStar
    ) {
        Pageable pageable = null;
        if (sortDir != null && sortField != null) {
            Sort sort = Sort.by(sortField);
            sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
            pageable = PageRequest.of(pageNum, pageSize, sort);
        }else {
            pageable = PageRequest.of(pageNum, pageSize);
        }

        Page<BaseProduct> baseProducts = baseProductRepository.findByCategoryBrandPriceBetween(categoryName, brandNames, startPrice, endPrice, pageable);
        List<BaseProduct> baseProductList = baseProducts.getContent();
        List<BaseProductGetListDto> target = baseProductList.stream().map(baseProduct -> {
            // Todo : get default attribute of baseProduct
            String imgUrl = "";
            Double price = 1000000.0;
            float averageRating = 5;
            int soldNum = 0 ;
            return BaseProductGetListDto.fromModel(baseProduct, imgUrl, price, averageRating,soldNum);
        }).toList();
        return new PageableData<BaseProductGetListDto>(
                pageNum,
                pageSize,
                baseProducts.getTotalElements(),
                baseProducts.getTotalPages(),
                target
        );
    }


    private void setProductAttributes(BaseProduct baseProduct, List<Long> attributeIds) {
        List<ProductAttribute> attributes = productAttributeRepository.findByIds(attributeIds);
        attributes.forEach(productAttribute -> {
            productAttribute.setBaseProduct(baseProduct);
        });
        productAttributeRepository.saveAllAndFlush(attributes);
    }


    public void setProductVariants(BaseProduct baseProduct, List<ProductPostDto> productPosts) {
        productPosts.forEach(productDto -> {
            Product product = Product.builder()
                    .status(true)
                    .image(productDto.image())
                    .baseProduct(baseProduct)
                    .quantity(productDto.quantity())
                    .price(productDto.price())
                    .build();
            productRepository.saveAndFlush(product);
            setProductImages(product, productDto.productImageIds());
            setProductAttributeValues(product, productDto.productOptionValueIds());
        });
    }

    private void setProductAttributeValues(Product product, List<Long> productAttributeValueIds) {
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


    private List<ProductDto> getProductVariantsById(Long baseProductId) {
        List<Product> products = productRepository.findByBaseProductId(baseProductId);
        List<ProductDto> target = products.stream().map(product -> {
            Long productId = product.getId();
            List<ProductImageDto> productImageDtos = product.getProductImages().stream().map(productImage -> {
                String url = mediaFeignClient.getUrlById(productImage.getUrl()).getBody();
                return ProductImageDto.fromModel(productImage, url);
            }).toList();
            List<ProductVariation> productVariations = productVariationRepository.findByProductId(productId);
            List<ProductAttributeValue> productAttributeValues = productVariations.stream().map(ProductVariation::getProductAttributeValue).toList();
            List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(productAttributeValue -> {
                String image = "";
                if (productAttributeValue.getImage() != "" && productAttributeValue.getImage() != null) {
                    image = mediaFeignClient.getUrlById(productAttributeValue.getImage()).getBody();
                }
                return ProductAttributeValueDto.fromModel(productAttributeValue, image);
            }).toList();
            String image = mediaFeignClient.getUrlById(product.getImage()).getBody();
            return ProductDto.fromModel(product, productImageDtos, productAttributeValueDtos, image);
        }).toList();
        return target;
    }

    private void setProductCategory(BaseProduct baseProduct, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        baseProduct.setCategory(category);
    }
    private  List<String>  getBreadcrumb(Integer categoryId, String productName) {
        List<String> categoryList = new ArrayList<>();
        Category category = categoryRepository.findByIdCustom(categoryId).orElseThrow();
        while(category.hasParent()) {
            categoryList.add(category.getName());
            category = categoryRepository.findByIdCustom(category.getParent().getId()).orElseThrow();
        }
        categoryList.add(category.getName());
        Collections.reverse(categoryList);
        categoryList.add(0, "Trang chủ");
        categoryList.add(productName);
        return categoryList;
    }
    private void setProductImages(Product product, List<ProductImageDto> productImages) {
        List<ProductImage> productImageList = new ArrayList<>();
        if (productImages != null && productImages.size() > 0) {
            for(ProductImageDto productImage: productImages) {
                ProductImage newProductImage = ProductImage.builder()
                        .url(productImage.url())
                        .product(product)
                        .build();
                productImageList.add(newProductImage);
            }
            productImageRepository.saveAllAndFlush(productImageList);
            product.setProductImages(productImageList);
        }
    }
    private void setBrand(BaseProduct baseProduct, Integer brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() ->
                new NotFoundException(Constants.ERROR_CODE.BRAND_NOT_FOUND, brandId));
        baseProduct.setBrand(brand);
    }
}
