package com.siki.review.repository;

import com.siki.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query("""
            select r
            from review r
            where r.baseProductId = :baseProductId
            """)
    Page<Review> findByProductId(@Param("baseProductId") Long baseProductId, Pageable pageable);


    @Query("""
            select r
            from review r
            where r.baseProductId = :baseProductId and r.ratingStar = :ratingStar
            """)
    Page<Review> findByRatingStar(@Param("ratingStar") int ratingStar, @Param("baseProductId") Long baseProductId, Pageable pageable);
}
