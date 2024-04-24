package com.siki.product.repository;

import com.siki.product.model.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {


    @Query("""
            select pav
            from ProductAttributeValue pav
            join fetch pav.productAttribute pa
            where pav.id = :productAttributeValueId
            """)
    Optional<ProductAttributeValue> findByIdCustom(@Param("productAttributeValueId") Long productAttributeValueId );

}
