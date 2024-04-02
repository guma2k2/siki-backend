package com.siki.product.repository;

import com.siki.product.model.ProductAttributeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductAttributeSetRepository extends JpaRepository<ProductAttributeSet, Integer> {

    @Query("""
            select pts
            from ProductAttributeSet pts
            join fetch pts.productAttributes
            where pts.id = :id
            """)
    Optional<ProductAttributeSet> getByProductId(@Param("id") Integer id);
}
