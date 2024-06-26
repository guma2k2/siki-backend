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

    @Query("""
            select bp
            from BaseProduct bp
            where bp.name = :name
            """)
   Optional<BaseProduct> findByName(@Param("name") String name);

    @Query("""
            select bp
            from BaseProduct bp
            left join fetch bp.brand b
            where b.id = :id
            """)
    List<BaseProduct> findByBrandId(@Param("id") Integer id);


    @Query("""
            select bp
            from BaseProduct bp
            join fetch bp.brand
            join fetch bp.category
            where bp.slug = :slug
            """)
    Optional<BaseProduct> findBySlug(@Param("slug") String slug);

    @Query("""
            select bp
            from BaseProduct bp
            join fetch bp.category c
            where c.id = :categoryId
            """)
    List<BaseProduct> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("""
            select bp
            from BaseProduct bp
            join fetch bp.brand
            join fetch bp.category
            """)
    List<BaseProduct> findAllCustom();


    @Query("""
        select bp
        from BaseProduct bp
        join fetch bp.brand
        join fetch bp.category
        where bp.name like %:text%
        """)
    List<BaseProduct> findByNameCustom(@Param("text")String text);

}
