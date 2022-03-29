package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.CartDTO;
import com.capstone_project.hbts.entity.Cart;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl {

    private final CartRepository cartRepository;
    
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    public void addToCart(int roomTypeId, int hotelId, int quantity, int bookedQuantity, int userId, Date dateIn, Date dateOut) {
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
        cart.setBookedQuantity(bookedQuantity);
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(int userId) {
        cartRepository.clearCart(userId);
    }

    public List<CartDTO> getAllCartItem(int userId) {
        return cartRepository.getAllCartItem(userId).stream().map(item -> modelMapper.map(item, CartDTO.class))
                .collect(Collectors.toList());
    }

    public Integer getTotalNumberItemInCart(int userId) {
        return Objects.requireNonNullElse(cartRepository.getTotalNumberItemInCart(userId), 0);
    }

    public Integer getHotelIdByUserId(int userId) {
        Integer hotelId = cartRepository.getHotelIdByUserId(userId);
        return Objects.requireNonNullElse(hotelId, 0);
    }

    public void deleteCartItem(int cartId) {
        cartRepository.deleteById(cartId);
    }

}
