package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.constant.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Report.ReviewDTO;
import com.capstone_project.hbts.request.ReviewRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.DataPagingResponse;
import com.capstone_project.hbts.service.ReviewServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class ReviewResource {

    private final ReviewServiceImpl reviewService;

    private final DataDecryption dataDecryption;

    public ReviewResource(ReviewServiceImpl reviewService, DataDecryption dataDecryption) {
        this.reviewService = reviewService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote for admin/user/provider can view
     */
    @GetMapping("/reviews")
    public ResponseEntity<?> getReview(@RequestParam String hotelId, @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                       @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to get list review by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            Page<ReviewDTO> pageReview = reviewService.loadReview(id, PageRequest.of(page, pageSize));
            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(pageReview.getContent(),
                    pageReview.getTotalElements(), page, pageReview.getSize());
            return ResponseEntity.ok().body(new ApiResponse<>(200, dataPagingResponse, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for user can add a new review about hotel's service
     */
    @PostMapping("/add-review")
    public ResponseEntity<?> getReview(@RequestBody ReviewRequest reviewRequest) {
        log.info("REST request to add a new review ");
        try {
            if (reviewService.isUserReviewAboutBooking(reviewRequest.getUserBookingId())) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_REVIEW_001_LABEL));
            } else {
                reviewService.addReview(reviewRequest);
                return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
