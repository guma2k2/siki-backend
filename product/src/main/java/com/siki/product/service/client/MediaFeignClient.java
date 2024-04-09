package com.siki.product.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "medias", url = "http://localhost:8090/api/medias")
public interface MediaFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<String> getUrlById(@PathVariable("id") String id);
}
