package com.siki.product.repository;

import com.siki.product.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query("""
            select r
            from review r
            left join fetch r.product p
            left join fetch p.baseProduct b
            where b.id = :baseProductId
            """)
    Page<Review> findByProductId(@Param("baseProductId") Long baseProductId, Pageable pageable);

    @Query("""
            select r
            from review r
            left join fetch r.product p
            left join fetch p.baseProduct b
            where b.id = :baseProductId
            """)
    List<Review> findByBaseProductId(@Param("baseProductId") Long baseProductId);


    @Query("""
            select r
            from review r
            left join fetch r.product p
            left join fetch p.baseProduct b
            where b.id = :baseProductId and r.ratingStar = :ratingStar
            """)
    Page<Review> findByRatingStar(@Param("ratingStar") int ratingStar, @Param("baseProductId") Long baseProductId, Pageable pageable);


}
