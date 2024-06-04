package com.siki.product.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orders", url = "http://localhost:8090/api/orders")
public interface OrderFeignClient {

    @GetMapping("/storefront/sold-num/product/{productId}")
     ResponseEntity<Long> getSoldNumByProduct(@PathVariable("productId") Long productId);
}
