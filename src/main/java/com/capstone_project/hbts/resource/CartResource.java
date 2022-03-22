package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.dto.CartDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class CartResource {

    private final CartService cartService;

    private final JwtTokenUtil jwtTokenUtil;

    public CartResource(CartService cartService, JwtTokenUtil jwtTokenUtil) {
        this.cartService = cartService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * @param jwttoken
     * @param roomTypeId
     * @param quantity
     * @param hotelId
     * @param dateIn
     * @param dateOut
     * @param bookedQuantity
     * @apiNote for user to add item to cart when they are booking
     * return
     */
    @PatchMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String jwttoken,
                                       @RequestParam int roomTypeId,
                                       @RequestParam int quantity,
                                       @RequestParam int hotelId,
                                       @RequestParam Date dateIn,
                                       @RequestParam Date dateOut,
                                       @RequestParam int bookedQuantity) {
        log.info("REST request to add item to cart");

        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            List<CartDTO> carts = cartService.getAllCartItem(userId);
            if (!carts.isEmpty()) {
                // check if user choose an other day, delete the old one and add the new one
                if (carts.size() == 1 && !dateIn.equals(carts.get(0).getDateIn()) ||
                        !dateOut.equals(carts.get(0).getDateOut())) {
                    cartService.deleteCartItem(carts.get(0).getId());
                    // add the new one
                    cartService.addToCart(roomTypeId, hotelId, quantity, bookedQuantity, userId, dateIn, dateOut);
                    return ResponseEntity.ok()
                            .body(new ApiResponse<>(200, null,
                                    null, null));
                }
            }
            // check if user picked over number item allowed
            int totalItemInCart = cartService.getTotalNumberItemInCart(userId);
            if (totalItemInCart >= 2 || totalItemInCart != 0 && totalItemInCart + quantity > 2) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(400, null,
                                ErrorConstant.ERR_ITEM_004, ErrorConstant.ERR_ITEM_004_LABEL));
            }
            // check if user pick 2 room of 2 hotel
            int hotel_id = cartService.getHotelIdByUserId(userId);
            if (hotel_id != 0) {
                if (hotel_id != hotelId) {
                    return ResponseEntity.badRequest()
                            .body(new ApiResponse<>(400, null,
                                    ErrorConstant.ERR_ITEM_005, ErrorConstant.ERR_ITEM_005_LABEL));
                }
            }
            cartService.addToCart(roomTypeId, hotelId, quantity, bookedQuantity, userId, dateIn, dateOut);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param jwttoken
     * @apiNote for user to clear cart
     * return
     */
    @PatchMapping("/clear-cart")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to clear all item in cart");

        try {
            int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
            cartService.clearCart(userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param jwttoken
     * @apiNote for user to get all item in cart
     * return
     */
    @GetMapping("/get-item-cart")
    public ResponseEntity<?> getAllCartItem(@RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get all item in cart");

        try {
            int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
            List<CartDTO> cartDTOList = cartService.getAllCartItem(userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, cartDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
