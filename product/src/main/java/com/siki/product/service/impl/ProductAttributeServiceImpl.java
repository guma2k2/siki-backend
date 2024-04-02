package com.siki.product.service.impl;

import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributePostDto;
import com.siki.product.dto.product.ProductAttributeSetDto;
import com.siki.product.model.ProductAttribute;
import com.siki.product.model.ProductAttributeSet;
import com.siki.product.model.ProductAttributeValue;
import com.siki.product.repository.ProductAttributeRepository;
import com.siki.product.repository.ProductAttributeSetRepository;
import com.siki.product.repository.ProductAttributeValueRepository;
import com.siki.product.service.ProductAttributeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductAttributeSetRepository productAttributeSetRepository;

    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository, ProductAttributeValueRepository productAttributeValueRepository, ProductAttributeSetRepository productAttributeSetRepository) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
        this.productAttributeSetRepository = productAttributeSetRepository;
    }

    @Override
    public ProductAttributeSetDto save(List<ProductAttributePostDto> productAttributePostDtoList,
                                          String attribute_set_name) {
        ProductAttributeSet productAttributeSet = ProductAttributeSet.builder()
                .name(attribute_set_name)
                .build();
        productAttributeSetRepository.saveAndFlush(productAttributeSet);
        List<ProductAttribute> attributes = new ArrayList<>();
        List<ProductAttributeDto> target = new ArrayList<>();
        productAttributePostDtoList.forEach(productAttributePostDto -> {
            ProductAttribute productAttribute = ProductAttribute.builder()
                    .name(productAttributePostDto.name())
                    .productAttributeSet(productAttributeSet)
                    .build();
            productAttributeRepository.saveAndFlush(productAttribute);
            List<ProductAttributeValue> productAttributeValues = new ArrayList<>();
            productAttributePostDto.productAttributeValues().forEach(item -> {
                ProductAttributeValue productAttributeValue = ProductAttributeValue.builder()
                        .productAttribute(productAttribute)
                        .value(item)
                        .build();
                productAttributeValues.add(productAttributeValue);
            });
            List<ProductAttributeValue> savedProductAttributeValues = productAttributeValueRepository.saveAllAndFlush(productAttributeValues);
            productAttribute.setProductAttributeValues(savedProductAttributeValues);
            attributes.add(productAttribute);
            ProductAttributeDto productAttributeDto = ProductAttributeDto.fromModel(productAttribute);
            target.add(productAttributeDto);
        });
        productAttributeSet.setProductAttributes(attributes);
        ProductAttributeSetDto productAttributeSetDto = ProductAttributeSetDto.fromModel(productAttributeSet, target);
        return productAttributeSetDto;
    }

    @Override
    public ProductAttributeDto getById(Long id) {
        ProductAttribute productAttribute = productAttributeRepository.get(id).orElseThrow();
        return ProductAttributeDto.fromModel(productAttribute);
    }
}
