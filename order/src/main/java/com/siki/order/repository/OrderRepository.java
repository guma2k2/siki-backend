package com.siki.order.repository;

import com.siki.order.enums.OrderStatus;
import com.siki.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query("""
            UPDATE Order o
            SET o.status = :orderStatus
            WHERE o.id = :orderId
            """)
    @Modifying
    void updateStatusById(@Param("orderId") Long cartId,
                            @Param("orderStatus") OrderStatus orderStatus);



    @Query("""
            select o
            from Order o
            left  join fetch  o.orderDetails
            where o.id = :id
            """)
    Optional<Order> findByIdCustom(@Param("id") Long id);
    @Query("""
            select o
            from Order o
            left  join fetch  o.orderDetails
            """)
    List<Order> findAllCustom();


    @Query("""
            select o
            from Order o
            left  join fetch  o.orderDetails
            where o.status = :status
            """)
    List<Order> findAllByStatus(@Param("status") OrderStatus orderStatus);




}
