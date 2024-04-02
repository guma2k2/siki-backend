package com.siki.product.repository;

import com.siki.product.model.Product;
import com.siki.product.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {


    @Query("""
            select pa
            from ProductAttribute pa
            join fetch pa.productAttributeValues
            where pa.id = :id
            """)
    Optional<ProductAttribute> get(@Param("id") Long id);

}
