package com.siki.product.repository;

import com.siki.product.model.BaseProduct;
import com.siki.product.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

   @Query("""
           select p
           from BaseProduct p
           left join fetch p.category c
           left join fetch p.brand b
           where c.name = :categoryName and b.name in :brandNames
           """)
   Page<BaseProduct> findByCategoryBrand(@Param("categoryName") String categoryName,
                                         @Param("brandNames") String[] brandNames,
                                         Pageable pageable
   );




}
