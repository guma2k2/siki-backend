package com.siki.product.service.impl;

import com.siki.product.dto.PageableData;
import com.siki.product.dto.StoreDto;
import com.siki.product.dto.product.*;
import com.siki.product.exception.*;
import com.siki.product.model.*;
import com.siki.product.repository.*;
import com.siki.product.service.ProductService;
import com.siki.product.service.client.OrderFeignClient;
import com.siki.product.utils.Constants;
import com.siki.product.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final ReviewRepository reviewRepository;

    private final OrderFeignClient orderFeignClient;

    public ProductServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository, BrandRepository brandRepository, BaseProductRepository baseProductRepository, CategoryRepository categoryRepository, ProductVariationRepository productVariationRepository, ProductAttributeValueRepository productAttributeValueRepository, ProductAttributeRepository productAttributeRepository, ReviewRepository reviewRepository, OrderFeignClient orderFeignClient) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.brandRepository = brandRepository;
        this.baseProductRepository = baseProductRepository;
        this.categoryRepository = categoryRepository;
        this.productVariationRepository = productVariationRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.reviewRepository = reviewRepository;
        this.orderFeignClient = orderFeignClient;
    }


    @Override
    @Transactional
    public void create(BaseProductPostDto baseProductPostDto) {
        if (isBaseProductNameExisted(baseProductPostDto.name())) {
            throw new DuplicatedException(Constants.ERROR_CODE.PRODUCT_NAME_IS_EXISTED);
        }
        BaseProduct baseProduct = BaseProduct.builder()
                .name(baseProductPostDto.name())
                .slug(StringUtils.toSlug(baseProductPostDto.name()))
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
        Integer categoryId = baseProduct.getCategory().getId();
        List<BaseProduct> baseProductList = baseProductRepository.findByCategoryId(categoryId);
        List<BaseProductGetListDto> relatedProducts = getBaseProductGetListDto(baseProductList);
        List<ProductAttribute> productAttributes = productAttributeRepository.findByBaseProductId(baseProductId);
        List<ProductAttributeDto> productAttributeDtos =
                productAttributes.stream().map(productAttribute -> {
                    List<ProductAttributeValue> productAttributeValues = productAttribute.getProductAttributeValues();
                    List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(ProductAttributeValueDto::fromModel).toList();
                    return ProductAttributeDto.fromModel(productAttribute, productAttributeValueDtos);
                }).toList();
        List<ProductDto> productVariants = getProductVariantsById(baseProductId);
        List<String> breadcrumb = getBreadcrumb(baseProduct.getCategory().getId(), baseProduct.getName());
        StoreDto storeDto = null;
        return BaseProductDto.fromModel(baseProduct, storeDto, productAttributeDtos  , productVariants, breadcrumb, relatedProducts);
    }

    @Override
    public ProductVariantDto findProductVariantById(Long productId) {
        Product product = productRepository.findByIdCustom(productId).orElseThrow(() -> new NotFoundException("product not found"));
        StoreDto store = null;
        if (productId % 2L == 0) {
            store = new StoreDto(1, "Tiki shop");
        } else {
            store = new StoreDto(2, "My store");
        }
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
                                                                      int[] ratingStars
    ) {
       Pageable pageable = null;
        if (sortDir != null && sortField != null) {
            Sort sort = Sort.by(sortField);
            sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
            pageable = PageRequest.of(pageNum, pageSize, sort);
        }else {
            pageable = PageRequest.of(pageNum, pageSize);
        }
        if (brandNames == null) {
            brandNames = new String[]{};
        }
        Page<BaseProduct> baseProducts = baseProductRepository.findByCategoryBrand(categoryName, brandNames, pageable);
        List<BaseProduct> baseProductList = baseProducts.getContent();
        if (startPrice != null && endPrice != null) {
            baseProductList = baseProductList.stream()
                    .filter(baseProduct -> baseProduct.getProducts().stream()
                            .anyMatch(product -> product.isDefault() && product.getPrice() >= startPrice && product.getPrice() <= endPrice))
                    .toList();

        }
        if (ratingStars != null) {
            Arrays.stream(ratingStars).sorted();
            baseProductList = baseProductList.stream()
                    .filter(baseProduct -> {
                        List<Review> reviews = reviewRepository.findByBaseProductId(baseProduct.getId());
                        float averageRating = getAverageRating(reviews);
                        if (averageRating > ratingStars[0]) {
                            return true;
                        } else {
                            return false ;
                        }
                    }).toList();
        }

        List<BaseProductGetListDto> target = getBaseProductGetListDto(baseProductList);
        return new PageableData<BaseProductGetListDto>(
                pageNum,
                pageSize,
                baseProducts.getTotalElements(),
                baseProducts.getTotalPages(),
                target
        );
    }

    private List<BaseProductGetListDto> getBaseProductGetListDto(List<BaseProduct> baseProductList) {
        List<BaseProductGetListDto> target = baseProductList.stream().map(baseProduct -> {
            Product product = productRepository.findByBaseProductIsDefaultId(baseProduct.getId()).orElseThrow();
            Long productId = product.getId();
            List<Review> reviews = reviewRepository.findByBaseProductId(baseProduct.getId());

            float averageRating = getAverageRating(reviews);
            long soldNum = 0 ;

            Optional<Product> defaultProduct = baseProduct.getProducts().stream().filter(product1 -> product1.isDefault()).findFirst();
            if (defaultProduct.isPresent())  {
                Long soldNumRes = orderFeignClient.getSoldNumByProduct(defaultProduct.get().getId()).getBody();
                soldNum = soldNumRes == null ? 0L : soldNumRes;
            }
            return BaseProductGetListDto.fromModel(baseProduct, product.getImage(), product.getPrice(), averageRating,soldNum, productId);
        }).toList();
        return target;
    }

    @Override
    public BaseProductDto getBySlug(String slug) {
        BaseProduct baseProduct = baseProductRepository.findBySlug(slug).orElseThrow();
        Integer categoryId = baseProduct.getCategory().getId();
        List<BaseProduct> baseProductList = baseProductRepository.findByCategoryId(categoryId);
        List<BaseProductGetListDto> relatedProducts = getBaseProductGetListDto(baseProductList);
        Long baseProductId = baseProduct.getId();
        List<ProductAttribute> productAttributes = productAttributeRepository.findByBaseProductId(baseProductId);
        List<ProductAttributeDto> productAttributeDtos =
                productAttributes.stream().map(productAttribute -> {
                    List<ProductAttributeValue> productAttributeValues = productAttribute.getProductAttributeValues();
                    List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(ProductAttributeValueDto::fromModel).toList();
                    return ProductAttributeDto.fromModel(productAttribute, productAttributeValueDtos);
                }).toList();
        List<ProductDto> productVariants = getProductVariantsById(baseProductId);
        List<String> breadcrumb = getBreadcrumb(baseProduct.getCategory().getId(), baseProduct.getName());
        StoreDto storeDto = null;
        return BaseProductDto.fromModel(baseProduct, storeDto,productAttributeDtos  , productVariants, breadcrumb, relatedProducts);
    }

    @Override
    public List<BaseProductGetListDto> getByCategory(Integer categoryId) {
        List<BaseProduct> baseProductList = baseProductRepository.findByCategoryId(categoryId);
        List<BaseProductGetListDto> target = getBaseProductGetListDto(baseProductList);
        return target;
    }

    private float getAverageRating(List<Review> reviews) {
        return Math.round((float) reviews.stream().mapToInt(review -> review.getRatingStar()).sum() / reviews.size());
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
                    .isDefault(productDto.isDefault())
                    .image(productDto.image())
                    .baseProduct(baseProduct)
                    .quantity(productDto.quantity())
                    .price(productDto.price())
                    .build();
            productRepository.saveAndFlush(product);
            setProductImages(product, productDto.productImages());
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
            List<ProductImageDto> productImageDtos = product.getProductImages().stream().map(ProductImageDto::fromModel).toList();
            List<ProductVariation> productVariations = productVariationRepository.findByProductId(productId);
            List<ProductAttributeValue> productAttributeValues = productVariations.stream().map(ProductVariation::getProductAttributeValue).toList();
            List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(ProductAttributeValueDto::fromModel).toList();
            return ProductDto.fromModel(product, productImageDtos, productAttributeValueDtos);
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


    private boolean isBaseProductNameExisted(String name) {
        return baseProductRepository.findByName(name).isPresent();
    }
}
