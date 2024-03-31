package com.siki.product.service.impl;

import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributePostDto;
import com.siki.product.model.ProductAttribute;
import com.siki.product.model.ProductAttributeValue;
import com.siki.product.repository.ProductAttributeRepository;
import com.siki.product.repository.ProductAttributeValueRepository;
import com.siki.product.service.ProductAttributeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;

    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository, ProductAttributeValueRepository productAttributeValueRepository) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
    }

    @Override
    public List<ProductAttributeDto> save(List<ProductAttributePostDto> productAttributePostDtoList) {
        List<ProductAttributeDto> target = new ArrayList<>();
        productAttributePostDtoList.forEach(productAttributePostDto -> {
            ProductAttribute productAttribute = ProductAttribute.builder()
                    .name(productAttributePostDto.name())
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
            ProductAttributeDto productAttributeDto = ProductAttributeDto.fromModel(productAttribute);
            target.add(productAttributeDto);
        });
        return target;
    }
}
