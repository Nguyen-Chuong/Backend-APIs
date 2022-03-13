package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.request.UserRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("api/v1")
public class UserResource {

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final DataDecryption dataDecryption;

    public UserResource(UserService userService, JwtTokenUtil jwtTokenUtil,
                        DataDecryption dataDecryption) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param userRequest
     * @apiNote for only user register, manager will be added by admin
     * return
     */
    // add a new admin account / assign by type
    // admin and manager account cannot be registered
    @PostMapping("/register/user")
//    @PreAuthorize("hasAuthority('ADMIN')") // get via table authority
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest){
        log.info("REST request to register a new user : {}", userRequest);

        if(userService.isEmailExist(userRequest.getEmail())){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_USER_004, ErrorConstant.ERR_USER_004_LABEL));
        }
        if(userService.isUsernameExist("u-" + userRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_USER_005, ErrorConstant.ERR_USER_005_LABEL));
        }
        try {
            userService.register(userRequest);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param jwttoken
     * @apiNote for user can get their profile, both admin/manager can use it
     * return
     */
    @GetMapping("/profile/user")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String jwttoken){
        log.info("REST request to get user profile");

        try {
            String username = jwtTokenUtil.getUsernameFromToken(jwttoken.substring(7));
            UserDTO userDTO = userService.getUserProfile(username);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, userDTO,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param oldPass
     * @param newPass
     * @param jwttoken
     * @apiNote for user can change their password, both admin/manager can use it
     * return
     */
    @PatchMapping("/change-password/user")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String jwttoken,
                                            @RequestParam String oldPass,
                                            @RequestParam String newPass){
        log.info("REST request to change user's password");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPasswordEncoded = bCryptPasswordEncoder.encode(newPass);

        String username = jwtTokenUtil.getUsernameFromToken(jwttoken.substring(7));
        String userPassword = userService.getOldPassword(username);

        if(!bCryptPasswordEncoder.matches(oldPass, userPassword)){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_USER_001, ErrorConstant.ERR_USER_001_LABEL));
        }else {
            try {
                userService.changeUserPassword(username, newPasswordEncoded);
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

    /**
     * @param userDTO
     * @apiNote for user to update their profile, both admin/manager can use it
     * return
     */
    @PatchMapping("/update-profile/user")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDTO userDTO){
        log.info("REST request to update an user : {}", userDTO);

        try{
            userService.updateUserProfile(userDTO);
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

    /**
     * @Param username
     * return
     */
    @GetMapping("/check/user/username/{username}")
    public ResponseEntity<?> isUsernameExist(@PathVariable String username){
        log.info("REST request to check duplicate user's username");

        try {
            boolean isUsernameExist = userService.isUsernameExist(username);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, isUsernameExist,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @Param email
     * return
     */
    @GetMapping("/check/user/email/{email}")
    public ResponseEntity<?> isEmailExist(@PathVariable String email){
        log.info("REST request to check duplicate user's email");

        try {
            boolean isEmailExist = userService.isEmailExist(email);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, isEmailExist,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    // api update vip status will be called when user complete a booking
    /**
     * @param jwttoken
     * @apiNote for user to update their vip status after completing a booking
     * return
     */
    @PatchMapping("/update-vip-status")
    public ResponseEntity<?> updateVipStatus(@RequestHeader("Authorization") String jwttoken){
        log.info("REST request to update user's vip status");

        try{
            int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
            userService.updateVipStatus(userId);
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

    /**
     * @param email
     * @param newPass
     * @apiNote for user who forgot their password can refresh new password via email
     * return
     */
    @PatchMapping("/authenticate/forgot-password")
    public ResponseEntity<?> changePassword(@RequestParam String email,
                                            @RequestParam String newPass){
        log.info("REST request to change user's password cuz they forgot them :) !");
        String emailDecrypted;
        try {
            emailDecrypted = dataDecryption.convertEncryptedDataToString(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPasswordEncoded = bCryptPasswordEncoder.encode(newPass);

        try {
            userService.changeForgotPassword(emailDecrypted, newPasswordEncoded);
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

    /**
     * @param jwttoken
     * @apiNote for user to delete their account, manager account can be disabled by admin
     * provider don't have feature to delete account but they can be disabled by admin
     * return
     */
    @PatchMapping("/delete-account/user")
    public ResponseEntity<?> deleteAccount(@RequestHeader("Authorization") String jwttoken){
        log.info("REST request to delete an user account");

        try{
            int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
            userService.deleteAccount(userId);
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
