package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.CartDTO;

import java.util.List;

public interface CartService {

    /**
     * add room type to cart
     * @param roomTypeId
     * @param quantity
     * @param userId
     * @param hotelId
     */
    void addToCart(int roomTypeId, int hotelId, int quantity, int userId);

    /**
     * to clear cart
     * @param userId
     */
    void clearCart(int userId);

    /**
     * to get all item in cart
     * @param userId
     */
    List<CartDTO> getAllCartItem(int userId);

    /**
     * to get total item in cart
     * @param userId
     */
    Integer getTotalNumberItemInCart(int userId);

    /**
     * to get hotel id by user id
     * @param userId
     */
    Integer getHotelIdByUserId(int userId);

    /**
     * to get room type by user id
     * @param userId
     */
    List<CartDTO> getRoomTypeByUserId(int userId);

    /**
     * to update quantity cart
     * @param cartId
     */
    void updateQuantityCart(int cartId);

}
