package com.siki.product.service.client;

import com.siki.product.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users", url = "http://localhost:8090/api/users")
public interface CustomerFeignClient {

     @GetMapping("/storefront/customer/{id}")
     ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") String id);
}
