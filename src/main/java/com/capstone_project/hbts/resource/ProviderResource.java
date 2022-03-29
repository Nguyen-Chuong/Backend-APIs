package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.constant.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Actor.ProviderDTO;
import com.capstone_project.hbts.request.ProviderRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.DataPagingResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.ProviderServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class ProviderResource {

    private final ProviderServiceImpl providerService;

    private final JwtTokenUtil jwtTokenUtil;

    private final DataDecryption dataDecryption;

    public ProviderResource(ProviderServiceImpl providerService, JwtTokenUtil jwtTokenUtil, DataDecryption dataDecryption) {
        this.providerService = providerService;this.jwtTokenUtil = jwtTokenUtil;this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote for provider can register
     */
    @PostMapping("/register/provider")
    public ResponseEntity<?> registerProvider(@RequestBody ProviderRequest providerRequest){
        log.info("REST request to register a new provider : {}", providerRequest);
        if(providerService.isEmailExist(providerRequest.getEmail())){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_004_LABEL));
        }
        if(providerService.isUsernameExist("p-" + providerRequest.getUsername())){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_005_LABEL));
        }
        try {
            providerService.register(providerRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can get their profile
     */
    @GetMapping("/profile/provider")
    public ResponseEntity<?> getProviderProfile(@RequestHeader("Authorization") String jwttoken){
        log.info("REST request to get provider profile");
        try {
            String username = jwtTokenUtil.getUsernameFromToken(jwttoken.substring(7));
            ProviderDTO providerDTO = providerService.getProviderProfile(username);
            return ResponseEntity.ok().body(new ApiResponse<>(200, providerDTO, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    @GetMapping("/check/provider/username/{username}")
    public ResponseEntity<?> isUsernameExist(@PathVariable String username){
        log.info("REST request to check duplicate provider username");
        try {
            boolean isUsernameExist = providerService.isUsernameExist(username);
            return ResponseEntity.ok().body(new ApiResponse<>(200, isUsernameExist, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    @GetMapping("/check/provider/email/{email}")
    public ResponseEntity<?> isEmailExist(@PathVariable String email){
        log.info("REST request to check duplicate provider email");
        try {
            boolean isEmailExist = providerService.isEmailExist(email);
            return ResponseEntity.ok().body(new ApiResponse<>(200, isEmailExist, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider to update their profile
     */
    @PatchMapping("/update-profile/provider")
    public ResponseEntity<?> updateProviderProfile(@RequestBody ProviderDTO providerDTO){
        log.info("REST request to update a provider : {}", providerDTO);
        try{
            providerService.updateProviderProfile(providerDTO);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider to change their password
     */
    @PatchMapping("/change-password/provider")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String jwttoken, @RequestParam String oldPass, @RequestParam String newPass){
        log.info("REST request to change provider's password");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPasswordEncoded = bCryptPasswordEncoder.encode(newPass);

        String username = jwtTokenUtil.getUsernameFromToken(jwttoken.substring(7));
        String userPassword = providerService.getOldPassword(username);

        if(!bCryptPasswordEncoder.matches(oldPass, userPassword)){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_001_LABEL));
        }else {
            try {
                providerService.changeProviderPassword(username, newPasswordEncoded);
                return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
            }catch (Exception e){ e.printStackTrace();
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
            }
        }
    }

    /**
     * @apiNote for admin/ manager to view all provider
     */
    @GetMapping("/get-all-provider")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllProvider(@RequestParam(defaultValue = ValidateConstant.PAGE) int page, @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize){
        log.info("REST request to get all provider for admin");
        try {
            Page<ProviderDTO> providerDTOPage = providerService.getAllProvider(PageRequest.of(page, pageSize));
            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(providerDTOPage.getContent(), providerDTOPage.getTotalElements(), page, providerDTOPage.getSize());
            return ResponseEntity.ok().body(new ApiResponse<>(200, dataPagingResponse, null));
        } catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for user who forgot their password can refresh new password via email
     */
    @PatchMapping("/authenticate/provider/forgot-password")
    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String newPass){
        log.info("REST request to change provider's password cuz they forgot them :) !");
        String emailDecrypted;
        try {
            emailDecrypted = dataDecryption.convertEncryptedDataToString(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPasswordEncoded = bCryptPasswordEncoder.encode(newPass);

        try {
            providerService.changeForgotPassword(emailDecrypted, newPasswordEncoded);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
