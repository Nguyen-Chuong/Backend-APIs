package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @apiNote for user to add item to cart when they are booking
     * return
     */
    @PatchMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String jwttoken,
                                       @RequestParam int roomTypeId,
                                       @RequestParam int quantity){
        log.info("REST request to add item to cart");

        if(quantity > 2){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_ITEM_004, ErrorConstant.ERR_ITEM_004_LABEL));
        }
        try{
            int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
            cartService.addToCart(roomTypeId, quantity, userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
