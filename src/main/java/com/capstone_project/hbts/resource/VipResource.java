package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.dto.VipDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.VipService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("api/v1")
public class VipResource {

    private final VipService vipService;

    public VipResource(VipService vipService) {
        this.vipService = vipService;
    }

    /**
     * return
     * @apiNote both user and admin can use this api
     */
    @GetMapping("/vip-info")
    public ResponseEntity<?> getVipStatus(){
        log.info("REST request to get vip table info");

        try {
            List<VipDTO> vipDTOList = vipService.getVipStatus();
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, vipDTOList,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    // modify vip table for admin
    /**
     * @param discount
     * @param rangeStart
     * @param rangeEnd
     * @param id
     * return
     */
    @PatchMapping("/update-vip-info")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateVipClass(@RequestParam int discount,
                                            @RequestParam int rangeStart,
                                            @RequestParam int rangeEnd,
                                            @RequestParam Integer id){
        log.info("REST request to update vip class for admin");

        try {
            vipService.updateVipClass(discount, rangeStart, rangeEnd, id);
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
