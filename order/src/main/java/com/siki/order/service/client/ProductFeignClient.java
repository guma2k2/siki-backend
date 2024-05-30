package com.siki.order.service.client;

import com.siki.order.dto.ProductVariantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "http://localhost:8090/api/products")
public interface ProductFeignClient {

    @GetMapping("/storefront/products-variant/{id}")
    ResponseEntity<ProductVariantDto> getByProductId(@PathVariable("id") Long id);
}