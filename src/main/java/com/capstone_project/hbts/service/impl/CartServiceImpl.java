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

import java.sql.Date;
import java.util.List;
import java.util.Objects;
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
    public void addToCart(int roomTypeId, int hotelId, int quantity, int userId, Date dateIn, Date dateOut) {
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
        // set hotel id
        cart.setHotelId(hotelId);
        cart.setDateIn(dateIn);
        cart.setDateOut(dateOut);
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

    @Override
    public Integer getTotalNumberItemInCart(int userId) {
        log.info("Request to get total number of item in cart");
        return Objects.requireNonNullElse(cartRepository.getTotalNumberItemInCart(userId), 0);
    }

    @Override
    public Integer getHotelIdByUserId(int userId) {
        log.info("Request to get hotel id by user id");
        Integer hotelId = cartRepository.getHotelIdByUserId(userId);
        return Objects.requireNonNullElse(hotelId, 0);
    }

    @Override
    public List<CartDTO> getRoomTypeByUserId(int userId) {
        log.info("Request to get room type id by user id");
        return cartRepository.getRoomTypeByUserId(userId)
                .stream()
                .map(item -> modelMapper.map(item, CartDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateQuantityCart(int cartId) {
        log.info("Request to update quantity cart");
        cartRepository.updateQuantityCart(cartId);
    }

    @Override
    public void deleteCartItem(int cartId) {
        log.info("Request to delete item cart");
        cartRepository.deleteById(cartId);
    }

}
