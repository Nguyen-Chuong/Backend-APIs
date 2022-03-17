package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Cart;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.CartRepository;
import com.capstone_project.hbts.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addToCart(int roomTypeId, int quantity, int userId) {
        log.info("Request to add room type to cart");
        Cart cart = new Cart();
        // set room type id
        cart.setRoomTypeId(roomTypeId);
        // set quantity
        cart.setQuantity(quantity);
        // set user
        Users users = new Users();
        users.setId(userId);
        cart.setUsers(users);
        cartRepository.save(cart);
    }

}
