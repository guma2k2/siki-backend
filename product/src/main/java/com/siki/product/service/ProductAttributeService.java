package com.siki.product.service;

import com.siki.product.dto.product.ProductAttributeDto;
import com.siki.product.dto.product.ProductAttributePostDto;
import com.siki.product.dto.product.ProductAttributeSetDto;
import com.siki.product.dto.product.ProductAttributeSetPostDto;

import java.util.List;

public interface ProductAttributeService {
    ProductAttributeSetDto save(ProductAttributeSetPostDto productAttributeSetPostDto);

    ProductAttributeDto getById(Long id);
}
