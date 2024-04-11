package com.siki.product.service.impl;

import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.brand.BrandPostDto;
import com.siki.product.model.Brand;
import com.siki.product.repository.BrandRepository;
import com.siki.product.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    @Override
    public void create(BrandPostDto brandPostDto) {
        Brand brand = Brand.builder()
                .name(brandPostDto.name())
                .logo(brandPostDto.logo())
                .build();
        brandRepository.save(brand);
    }

    @Override
    public BrandDto update(BrandPostDto brandPostDto, Integer brandId) {
        Brand brandFound = brandRepository.findById(brandId).orElseThrow();
        brandFound.setName(brandPostDto.name());
        brandFound.setLogo(brandPostDto.logo());
        brandRepository.save(brandFound);
        return BrandDto.fromModel(brandFound);
    }

    @Override
    public void delete(Integer brandId) {
        Brand brandFound = brandRepository.findById(brandId).orElseThrow();
        brandRepository.delete(brandFound);
    }
}
