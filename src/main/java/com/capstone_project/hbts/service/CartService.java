package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.CartDTO;

import java.sql.Date;
import java.util.List;

public interface CartService {

    /**
     * add room type to cart
     */
    void addToCart(int roomTypeId, int hotelId, int quantity, int bookedQuantity, int userId, Date dateIn, Date dateOut);

    /**
     * to clear cart
     */
    void clearCart(int userId);

    /**
     * to get all item in cart
     */
    List<CartDTO> getAllCartItem(int userId);

    /**
     * to get total item in cart
     */
    Integer getTotalNumberItemInCart(int userId);

    /**
     * to get hotel id by user id
     */
    Integer getHotelIdByUserId(int userId);

    /**
     * to delete an item in cart
     */
    void deleteCartItem(int cartId);

}
