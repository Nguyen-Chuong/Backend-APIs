package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.CartDTO;
import com.capstone_project.hbts.entity.Cart;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.CartRepository;
import com.capstone_project.hbts.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
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

    @Override
    @Transactional
    public void clearCart(int userId) {
        log.info("Request to clear cart");
        cartRepository.clearCart(userId);
    }

    @Override
    public List<CartDTO> getAllCartItem(int userId) {
        log.info("Request to get all item in cart");
        return cartRepository.getAllCartItem(userId)
                .stream()
                .map(item -> modelMapper.map(item, CartDTO.class))
                .collect(Collectors.toList());
    }

}
