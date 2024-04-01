package com.siki.product.repository;

import com.siki.product.model.ProductAttributeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeSetRepository extends JpaRepository<ProductAttributeSet, Integer> {
}
