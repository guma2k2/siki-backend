package com.siki.product.dto;

import java.util.List;

public record PageableData<T>(
        int pageNum,
        int pageSize,
        long totalElements,
        int totalPages,
        List<T> data
) {
}
