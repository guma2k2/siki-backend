package com.siki.product.service;

import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.brand.BrandPostDto;

public interface BrandService {
    void create(BrandPostDto brandPostDto);

    BrandDto update(BrandPostDto brandPostDto, Integer brandId);

    void delete(Integer brandId);

}
