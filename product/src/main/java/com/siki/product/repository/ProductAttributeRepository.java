package com.siki.product.repository;

import com.siki.product.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    @Query("""
            select pa
            from ProductAttribute pa
            where pa.id in :ids
            """)
    List<ProductAttribute> findByIds(@Param("ids")List<Long> ids);

    @Query("""
            select pa
            from ProductAttribute pa
            join fetch pa.baseProduct bp
            left join fetch pa.productAttributeValues
            where bp.id = :baseProductId
            """)
    List<ProductAttribute> findByBaseProductId(@Param("baseProductId") Long baseProductId);
}
