package com.siki.order.repository;

import com.siki.order.enums.OrderStatus;
import com.siki.order.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    @Query("""
            select sum(o.quantity)
            from OrderDetail o
            join o.order
            where o.productId = :productId and o.order.status = :status
            group by o.productId
            """)
    Long getSoldNumByProduct(@Param("productId") Long productId, @Param("status")OrderStatus status);

    @Query("""
            SELECT od.productId
            FROM OrderDetail od
            GROUP BY od.productId 
            ORDER BY SUM(od.quantity) DESC
            """)
    List<Long> findTopProductsByQuantity();


}
