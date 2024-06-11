package com.siki.product.service.impl;

import com.siki.product.dto.brand.BrandDto;
import com.siki.product.dto.brand.BrandPostDto;
import com.siki.product.model.BaseProduct;
import com.siki.product.model.Brand;
import com.siki.product.repository.BaseProductRepository;
import com.siki.product.repository.BrandRepository;
import com.siki.product.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BaseProductRepository baseProductRepository;
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
    public boolean delete(Integer brandId) {
        List<BaseProduct> baseProductList = baseProductRepository.findByBrandId(brandId);
        if (baseProductList.isEmpty()) {
            Brand brandFound = brandRepository.findById(brandId).orElseThrow();
            brandRepository.delete(brandFound);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<BrandDto> getAllBrands() {
        List<Brand> brandList = brandRepository.findAll();
        return brandList.stream().map(BrandDto::fromModel).toList();
    }
}
