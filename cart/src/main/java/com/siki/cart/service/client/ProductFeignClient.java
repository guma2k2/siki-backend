package com.siki.cart.service.client;

import com.siki.cart.dto.ProductVariantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "products", url = "http://localhost:8090/api/products")
public interface ProductFeignClient {

     @GetMapping("/storefront/products-variant/{id}")
     ResponseEntity<ProductVariantDto> getByProductId(@PathVariable("id") Long id);
}
