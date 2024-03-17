package com.siki.product.dto.product;


public record ProductDto (
         Long id,
         String name,

         String description,

         int quantity,

         boolean status,

         Long storeId
) {
}
