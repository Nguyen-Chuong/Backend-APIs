package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.request.ImageRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class ImageResource {

    private final ImageService imageService;

    private final DataDecryption dataDecryption;

    public ImageResource(ImageService imageService, DataDecryption dataDecryption) {
        this.imageService = imageService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote for provider can add a list image to their room type
     */
    @PostMapping("/add-list-image")
    public ResponseEntity<?> addListImageForRoomType(@RequestBody ImageRequest imageRequest) {
        log.info("REST request to add a list image to provider's room type");
        int totalImageInDb = imageService.getTotalNumberRoomTypeImage(imageRequest.getRoomTypeId());
        if (totalImageInDb >= 15 || totalImageInDb + imageRequest.getSourceUrl().size() > 15) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_ITEM_003_LABEL));
        }
        try {
            imageService.addListImageToRoomType(imageRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can delete list image
     */
    @DeleteMapping("/delete-image")
    public ResponseEntity<?> deleteImage(@RequestParam String roomTypeId) {
        log.info("REST request to delete list image for provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            imageService.deleteListImage(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
