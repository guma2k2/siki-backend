package com.siki.product.service;

import com.siki.product.dto.PageableData;
import com.siki.product.dto.product.*;

import java.util.List;

public interface ProductService {
    void create(BaseProductPostDto baseProductPostDto);
    
    BaseProductDto getById(Long productId);

    ProductVariantDto findProductVariantById(Long productId);

    PageableData<BaseProductGetListDto> getProductByMultiQuery(String categoryName, String[] brandNames, int pageNum, int pageSize, String sortDir, String sortField, Double startPrice, Double endPrice, int[] ratingStar);

    BaseProductDto getBySlug(String slug);

    List<BaseProductGetListDto> getByCategory(Integer categoryId);


    List<BaseProductGetListDto> getRecommendProducts(int limit);

    List<BaseProductGetListDto> getByName(String name);
}
