package com.siki.product.dto.product;

import com.siki.product.model.BaseProduct;

public record BaseProductGetListDto (
        Long id,
        String name,
        String slug,
        String image,
        Double price,
        float averageRating,
        long soldNum,
        Long productId
) {
    public static BaseProductGetListDto fromModel(BaseProduct baseProduct, String imgUrl, Double price, float averageRating, long soldNum, Long productId){
        return new BaseProductGetListDto(baseProduct.getId(), baseProduct.getName(), baseProduct.getSlug(), imgUrl, price, averageRating, soldNum, productId);
    }
}
