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

    @Query("""
            select p
            from Product p
            where p.name = :name and (:id is null or p.id != :id)
            """)
    Product findExistedName(@Param("name") String name, @Param("id") Long id);

    @Query("""
            select p
            from Product p
            left join fetch p.brand
            left join fetch p.category
            left join fetch p.productAttributeSet
            left join fetch p.productVariations
            where p.id = :id
            """)
    Optional<Product> findByIdCustom(@Param("id") Long id);


    @Query("""
            select p
            from Product p
            left join fetch p.productAttributeSet pas
            left join fetch p.productVariations
            where pas.id = :attributeSetId AND p != :baseProduct
            """)
    List<Product> findByAttributeSetId(@Param("attributeSetId") Integer attributeSetId, @Param("baseProduct") Product baseProduct);

    @Query("""
            select p
            from Product p
            left join fetch p.productImages
            where p in :products AND p != :baseProduct
            """)
    List<Product> findByAttributeSetIdReturnImages(@Param("products") List<Product> products, @Param("baseProduct") Product baseProduct);


    @Query(value = """
            select p
            from Product p
            left join fetch p.productImages
            where p = :product
            """)
    Optional<Product> findByProduct(@Param("product") Product product);
}
