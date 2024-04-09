package com.siki.product.service.impl;

import com.siki.product.dto.product.*;
import com.siki.product.model.*;
import com.siki.product.repository.*;
import com.siki.product.service.ProductAttributeService;
import com.siki.product.service.client.MediaFeignClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;

    private final MediaFeignClient mediaFeignClient;


    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository, ProductAttributeValueRepository productAttributeValueRepository, MediaFeignClient mediaFeignClient) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeValueRepository = productAttributeValueRepository;
        this.mediaFeignClient = mediaFeignClient;
    }

    @Override
    public List<ProductAttributeDto> save(List<ProductAttributePostDto> productAttributePosts) {
        List<ProductAttributeDto> target = new ArrayList<>();
        productAttributePosts.forEach(productAttributePostDto -> {
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
            List<ProductAttributeValueDto> productAttributeValueDtos = productAttributeValues.stream().map(productAttributeValue -> {
                String image = "";
                if (productAttributeValue.getImage() != "" && productAttributeValue.getImage() != null) {
                    image = mediaFeignClient.getUrlById(productAttributeValue.getImage()).getBody();
                }
                return ProductAttributeValueDto.fromModel(productAttributeValue, image);
            }).toList();
            ProductAttributeDto productAttributeDto = ProductAttributeDto.fromModel(productAttribute, productAttributeValueDtos);
            target.add(productAttributeDto);
        });
        return target;
    }

    @Override
    public ProductAttributeDto getById(Long id) {
        return null;
    }
}
