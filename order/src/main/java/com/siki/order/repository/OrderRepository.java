package com.siki.order.repository;

import com.siki.order.enums.OrderStatus;
import com.siki.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("""
            select o
            from Order o
            left join fetch o.orderDetails
            where o.userId = :userId
            """)
    List<Order> findAllByUserId(@Param("userId") String userId);

    @Query("""
            select o
            from Order o
            left join fetch o.orderDetails
            where o.userId = :userId and o.status = :orderStatus
            """)
    List<Order> findAllByUserIdAndStatus(@Param("userId") String userId, @Param("orderStatus")OrderStatus orderStatus) ;
}
