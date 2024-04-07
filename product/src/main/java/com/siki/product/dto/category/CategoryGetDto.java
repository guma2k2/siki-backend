package com.siki.product.dto.category;

import com.siki.product.dto.product.BaseProductGetListDto;

import java.util.List;

public record CategoryGetDto(
        List<CategoryDto> categoryChildrens,
        List<BaseProductGetListDto> products
) {
}
