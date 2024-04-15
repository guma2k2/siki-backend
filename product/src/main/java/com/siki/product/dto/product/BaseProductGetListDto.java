package com.siki.product.dto.product;

import com.siki.product.model.BaseProduct;

public record BaseProductGetListDto (
        Long id,
        String name,
        String slug,
        String image,
        Double price,
        float averageRating,
        int soldNum
) {
    public static BaseProductGetListDto fromModel(BaseProduct baseProduct, String imgUrl, Double price, float averageRating, int soldNum){
        return new BaseProductGetListDto(baseProduct.getId(), baseProduct.getName(), baseProduct.getSlug(), imgUrl, price, averageRating, soldNum);
    }
}
