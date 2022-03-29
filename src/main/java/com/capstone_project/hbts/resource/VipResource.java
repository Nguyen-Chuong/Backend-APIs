package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.dto.VipDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.VipServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class VipResource {

    private final VipServiceImpl vipService;

    public VipResource(VipServiceImpl vipService) {
        this.vipService = vipService;
    }

    /**
     * @apiNote both user and admin can use this api
     */
    @GetMapping("/vip-info")
    public ResponseEntity<?> getVipStatus(){
        log.info("REST request to get vip table info");
        try {
            List<VipDTO> vipDTOList = vipService.getVipStatus();
            return ResponseEntity.ok().body(new ApiResponse<>(200, vipDTOList, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    @PatchMapping("/update-vip-info")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateVipClass(@RequestParam int discount, @RequestParam int rangeStart, @RequestParam int rangeEnd, @RequestParam Integer id){
        log.info("REST request to update vip class for admin");
        try {
            vipService.updateVipClass(discount, rangeStart, rangeEnd, id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        }catch (Exception e){ e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
