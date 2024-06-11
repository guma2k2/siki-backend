package com.siki.user.repository;

import com.siki.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("""
        select u
        from User u
        where u.email = :email 
    """)
    Optional<User> findByEmail(@Param("email")String email);

    @Query("""
        select u
        from User u
        where u.phoneNumber = :phoneNumber
    """)
    Optional<User> findByPhoneNumber(@Param("phoneNumber")String phoneNumber);
}
