package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.dto.Actor.ProviderDTO;
import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.request.JwtRequest;
import com.capstone_project.hbts.request.ProviderRequest;
import com.capstone_project.hbts.request.UserRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.JwtResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.CustomUserDetailsService;
import com.capstone_project.hbts.service.JwtServiceImpl;
import com.capstone_project.hbts.service.ProviderServiceImpl;
import com.capstone_project.hbts.service.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class JwtAuthenticationResource {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsService userDetailsService;

    private final UserServiceImpl userService;

    private final ProviderServiceImpl providerService;

    private final JwtServiceImpl jwtService;

    public JwtAuthenticationResource(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService, UserServiceImpl userService, ProviderServiceImpl providerService, JwtServiceImpl jwtService) {
        this.authenticationManager = authenticationManager;this.jwtTokenUtil = jwtTokenUtil;this.userDetailsService = userDetailsService;
        this.userService = userService;this.providerService = providerService;this.jwtService = jwtService;
    }

    /**
     * @apiNote for admin/manager/user can authenticate
     */
    @PostMapping("/authenticate/user")
    public ResponseEntity<?> createJsonWebTokenKeyForUser(@RequestBody UserRequest userRequest) {
        log.info("REST request to authenticate user request : {}", userRequest);
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        UserDTO userDTO = userService.loadUserByEmail(email);
        if(userDTO == null){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_003_LABEL));
        }
        if(userDTO.getStatus() == 0){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_008_LABEL));
        }
        try{
            // also call to method loadUserByUsername of customUserDetailsService (in web config)
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), password)); // client send
        } catch (BadCredentialsException e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_002_LABEL));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDTO.getId()+"", userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwt, userDTO.getType());

        // type manager or admin
        if(userDTO.getType() == 1 || userDTO.getType() == 2){
            JwtRequest jwtRequest = new JwtRequest(1, jwt);
            try{
                jwtService.saveTokenKeyForAdmin(jwtRequest);
            }catch (Exception e){ e.printStackTrace();
            }
        }
        return ResponseEntity.ok().body(new ApiResponse<>(200, jwtResponse, null));
    }

    /**
     * @apiNote for provider can authenticate
     */
    @PostMapping("/authenticate/provider")
    public ResponseEntity<?> createJsonWebTokenKeyForProvider(@RequestBody ProviderRequest providerRequest) {
        log.info("REST request to authenticate provider request : {}", providerRequest);
        String email = providerRequest.getEmail();
        String password = providerRequest.getPassword();
        ProviderDTO providerDTO = providerService.loadProviderByEmail(email);
        if(providerDTO == null){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_003_LABEL));
        }
        if(providerDTO.getStatus() == 0){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_008_LABEL));
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(providerDTO.getUsername(), password));
        } catch (BadCredentialsException e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_002_LABEL));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(providerDTO.getUsername());

        final String jwt = jwtTokenUtil.generateToken(providerDTO.getId()+"", userDetails);

        return ResponseEntity.ok().body(new ApiResponse<>(200, jwt, null));
    }

    /**
     * @apiNote for admin/ manager to get their key
     */
    @GetMapping("/authenticate/admin-manager/hbts102secret/jwt1key")
    public ResponseEntity<?> getJsonWebTokenKeyForAdmin(){
        log.info("REST request to get jwt token for admin");
        try {
            String jwt = jwtService.getTokenKeyForAdmin();
            return ResponseEntity.ok().body(new ApiResponse<>(200, jwt, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
