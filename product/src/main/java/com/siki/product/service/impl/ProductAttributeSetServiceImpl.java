package com.siki.product.service.impl;

import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributeSetDto;
import com.siki.product.model.ProductAttribute;
import com.siki.product.model.ProductAttributeSet;
import com.siki.product.repository.ProductAttributeSetRepository;
import com.siki.product.service.ProductAttributeSetService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductAttributeSetServiceImpl implements ProductAttributeSetService {

    private final ProductAttributeSetRepository productAttributeSetRepository;

    public ProductAttributeSetServiceImpl(ProductAttributeSetRepository productAttributeSetRepository) {
        this.productAttributeSetRepository = productAttributeSetRepository;
    }

    @Override
    public ProductAttributeSetDto findById(Integer id) {
        ProductAttributeSet productAttributeSet = productAttributeSetRepository.getByProductId(id).orElseThrow();

        List<ProductAttribute> attributeList = productAttributeSet.getProductAttributes();
        List<ProductAttributeDto> productAttributeDtos = attributeList.stream().map(ProductAttributeDto::fromModel).toList();
        return ProductAttributeSetDto.fromModel(productAttributeSet, productAttributeDtos);
    }
}
