package com.siki.product.service;

import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.brand.BrandPostDto;

import java.util.List;

public interface BrandService {
    void create(BrandPostDto brandPostDto);

    BrandDto update(BrandPostDto brandPostDto, Integer brandId);

    boolean delete(Integer brandId);

    List<BrandDto> getAllBrands();

}
