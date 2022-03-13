package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.constants.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Report.ReviewDTO;
import com.capstone_project.hbts.request.ReviewRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.DataPagingResponse;
import com.capstone_project.hbts.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("api/v1")
public class ReviewResource {

    private final ReviewService reviewService;

    private final DataDecryption dataDecryption;

    public ReviewResource(ReviewService reviewService, DataDecryption dataDecryption) {
        this.reviewService = reviewService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param hotelId
     * @param page
     * @param pageSize
     * @apiNote for admin/user/provider can view
     * return
     */
    @GetMapping("/reviews")
    public ResponseEntity<?> getReview(@RequestParam String hotelId,
                                       @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                       @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize){
        log.info("REST request to get list review by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            Page<ReviewDTO> pageReview = reviewService.loadReview(id, PageRequest.of(page,pageSize));

            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(pageReview.getContent(),
                    pageReview.getTotalElements(), page, pageReview.getSize());

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, dataPagingResponse,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param reviewRequest
     * @apiNote for user can add a new review about hotel's service
     * return
     */
    @PostMapping("/add-review")
    public ResponseEntity<?> getReview(@RequestBody ReviewRequest reviewRequest){
        log.info("REST request to add a new review ");

        try {
            reviewService.addReview(reviewRequest);

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
