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
            where b.slug = :baseProductSlug
            """)
    Page<Review> findByProductSlug(@Param("baseProductId") String baseProductSlug, Pageable pageable);

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
            where b.slug = :baseProductSlug and r.ratingStar in :ratingStars
            """)
    Page<Review> findByRatingStar(@Param("ratingStars") List<Integer> ratingStars, @Param("baseProductSlug") String baseProductSlug, Pageable pageable);


}
