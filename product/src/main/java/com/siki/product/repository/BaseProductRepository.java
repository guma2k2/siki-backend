package com.siki.product.repository;

import com.siki.product.model.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseProductRepository extends JpaRepository<BaseProduct, Long> {

    @Query("""
            select bp
            from BaseProduct bp
            join fetch bp.brand
            join fetch bp.category
            where bp.id = :id
            """)
    Optional<BaseProduct> findByIdCustom(@Param("id") Long id);

}
