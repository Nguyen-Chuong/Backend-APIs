package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.CartDTO;

import java.util.List;

public interface CartService {

    /**
     * add room type to cart
     * @param roomTypeId
     * @param quantity
     * @param userId
     */
    void addToCart(int roomTypeId, int quantity, int userId);

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

}
