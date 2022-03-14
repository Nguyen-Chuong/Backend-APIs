package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.dto.ImageDTO;
import com.capstone_project.hbts.request.ImageRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("api/v1")
public class ImageResource {

    private final ImageService imageService;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * @param imageRequest
     * @apiNote for provider can add a list image to their room type
     * return
     */
    @PostMapping("/add-list-image")
    public ResponseEntity<?> addListImageForRoomType(@RequestBody ImageRequest imageRequest) {
        log.info("REST request to add a list image to provider's room type");
        int totalImageInDb = imageService.getTotalNumberRoomTypeImage(imageRequest.getRoomTypeId());
        if(totalImageInDb >= 10 || totalImageInDb + imageRequest.getSourceUrl().size() > 10){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_ITEM_003, ErrorConstant.ERR_ITEM_003_LABEL));
        }
        try {
            imageService.addListImageToRoomType(imageRequest);
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
     * @param imageDTO
     * @apiNote for provider can update an image
     * return
     */
    @PatchMapping("/update-image")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO) {
        log.info("REST request to update an image for provider");

        try {
            imageService.updateImage(imageDTO);
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
     * @param imageIds
     * @apiNote for provider can delete list image
     * return
     */
    @DeleteMapping("/delete-image")
    public ResponseEntity<?> deleteImage(@RequestBody List<Integer> imageIds) {
        log.info("REST request to update an image for provider");

        try {
            imageService.deleteListImage(imageIds);
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

}
