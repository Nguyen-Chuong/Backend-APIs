package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Modifying
    @Query(value = "delete from heroku_4fe5c149618a3f9.cart where user_id = :userId", nativeQuery = true)
    void clearCart(@Param("userId") int userId);

}
