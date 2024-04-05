package com.siki.product.repository;

import com.siki.product.model.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    @Query("""
            select pv
            from ProductVariation pv
            left join fetch pv.product pvp
            left join fetch pv.productAttributeValue pav
            left join fetch pav.productAttribute
            where pvp.id = :productId
            """)
    List<ProductVariation> findByProductId(@Param("productId") Long productId);
}
