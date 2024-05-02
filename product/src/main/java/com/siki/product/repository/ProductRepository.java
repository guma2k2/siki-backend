package com.siki.product.repository;

import com.siki.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

   /* @Query("""
            select p
            from Product p
            where p.name = :name and (:id is null or p.id != :id)
            """)
    Product findExistedName(@Param("name") String name, @Param("id") Long id);*/

    @Query("""
            select p
            from Product p
            left join fetch p.productVariations
            where p.id = :id
            """)
    Optional<Product> findByIdCustom(@Param("id") Long id);




    @Query("""
            select p
            from Product p
            join fetch p.baseProduct bp
            left join fetch p.productImages
            where bp.id = :baseProductId
            """)
    List<Product> findByBaseProductId(@Param("baseProductId") Long baseProductId);

    @Query("""
            select p
            from Product p
            join fetch p.baseProduct bp
            left join fetch p.productImages
            where bp.id = :baseProductId and p.isDefault = 1
            """)
    Optional<Product> findByBaseProductIsDefaultId(@Param("baseProductId") Long baseProductId);

}
