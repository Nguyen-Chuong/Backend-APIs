package com.capstone_project.hbts.service;

public interface CartService {

    /**
     * add room type to cart
     * @param roomTypeId
     * @param quantity
     * @param userId
     */
    void addToCart(int roomTypeId, int quantity, int userId);

}
