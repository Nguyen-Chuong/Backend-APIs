package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Modifying
    @Query(value = "delete from heroku_4fe5c149618a3f9.cart where user_id = :userId", nativeQuery = true)
    void clearCart(@Param("userId") int userId);

    @Query(value = "select * from heroku_4fe5c149618a3f9.cart where user_id = :userId", nativeQuery = true)
    List<Cart> getAllCartItem(@Param("userId") int userId);

    @Query(value = "select sum(quantity) from heroku_4fe5c149618a3f9.cart where user_id = :userId", nativeQuery = true)
    Integer getTotalNumberItemInCart(@Param("userId") int userId);

    @Query(value = "select hotel_id from heroku_4fe5c149618a3f9.cart where user_id = :userId", nativeQuery = true)
    Integer getHotelIdByUserId(@Param("userId") int userId);

    @Query(value = "select * from heroku_4fe5c149618a3f9.cart where user_id = :userId", nativeQuery = true)
    List<Cart> getRoomTypeByUserId(@Param("userId") int userId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.cart set quantity = 2 where id = :cartId", nativeQuery = true)
    void updateQuantityCart(@Param("cartId") int cartId);

}
