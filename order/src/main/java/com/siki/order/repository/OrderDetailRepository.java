package com.siki.order.repository;

import com.siki.order.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    @Query("""
            select sum(o.quantity)
            from Orders o
            where o.productId = :productId
            group by :productId
            """)
    Long getSoldNumByProduct(@Param("productId") Long productId);
}
