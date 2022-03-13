package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.constants.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Booking.BookingListDTO;
import com.capstone_project.hbts.dto.Booking.UserBookingDTO;
import com.capstone_project.hbts.request.BookingRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.DataPagingResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.BookingService;
import com.capstone_project.hbts.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class BookingResource {

    private final BookingService bookingService;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final DataDecryption dataDecryption;

    public BookingResource(BookingService bookingService, UserService userService,
                           JwtTokenUtil jwtTokenUtil, DataDecryption dataDecryption) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param username
     * @apiNote both admin & user can call this
     * return
     */
    @GetMapping("/user-bookings")
    public ResponseEntity<?> getUserBooking(@RequestParam String username) {
        log.info("REST request to get list user's booking by username");
        String usernameDecrypted;
        try{
            usernameDecrypted = dataDecryption.convertEncryptedDataToString(username);
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        int userId = userService.getUserId(usernameDecrypted);
        try {
            List<UserBookingDTO> userBookingDTOList = bookingService.getAllBookings(userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, userBookingDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param reviewStatus
     * @param jwttoken
     * return
     */
    @GetMapping("/bookings-review/{reviewStatus}")
    public ResponseEntity<?> getUserBookingReview(@PathVariable int reviewStatus,
                                                  @RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get list user's booking need to review or not");

        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));

        try {
            List<UserBookingDTO> userBookingDTOList = bookingService.getAllBookingsReview(reviewStatus, userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, userBookingDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    // modify booking later when user cancelled or do smth
    // here

    /**
     * @param jwttoken
     * return
     */
    @GetMapping("/bookings-completed")
    public ResponseEntity<?> getNumberBookingsCompleted(@RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get number booking completed by user id");

        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));

        try {
            int numberBookingCompleted = bookingService.getNumberBookingsCompleted(userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, numberBookingCompleted,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param status
     * @param jwttoken
     * return
     */
    @GetMapping("/bookings-by-status/{status}")
    public ResponseEntity<?> getUserBookingByStatus(@PathVariable int status,
                                                    @RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get list user's booking by status");

        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));

        try {
            List<UserBookingDTO> userBookingDTOList = bookingService.getAllBookingsByStatus(status, userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, userBookingDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param page
     * @param pageSize
     * return
     */
    @GetMapping("/get-all-booking")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllUserBooking(@RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                               @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to get list user's booking for admin");

        try {
            Page<BookingListDTO> userBookingDTOList = bookingService.getAllBookingForAdmin(PageRequest.of(page, pageSize));

            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(userBookingDTOList.getContent(),
                    userBookingDTOList.getTotalElements(), page, userBookingDTOList.getSize());

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, dataPagingResponse,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param bookingId
     * return
     */
    @GetMapping("/booking")
    public ResponseEntity<?> getBookingById(@RequestParam String bookingId) {
        log.info("REST request to get user's booking by id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            UserBookingDTO userBookingDTO = bookingService.getBookingById(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, userBookingDTO,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param hotelId
     * @param page
     * @param pageSize
     * @apiNote for provider to view list booking in their hotel
     * return
     */
    @GetMapping("/bookings/hotel")
    public ResponseEntity<?> getBookingByHotelId(@RequestParam String hotelId,
                                                 @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                                 @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to get user's booking by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            Page<UserBookingDTO> userBookingDTOPage = bookingService.getBookingsByHotelId(id,
                    PageRequest.of(page, pageSize));

            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(userBookingDTOPage.getContent(),
                    userBookingDTOPage.getTotalElements(), page, userBookingDTOPage.getSize());

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, dataPagingResponse,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param bookingId
     * @apiNote for user to cancel booking
     * return
     */
    @PatchMapping("/cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestParam String bookingId) {
        log.info("REST request to cancel booking");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            bookingService.cancelBooking(id);
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
     * @param bookingRequest
     * @param jwttoken
     * @apiNote for user to add a new booking
     * return
     */
    @PostMapping("/add-booking")
    public ResponseEntity<?> addNewBooking(@RequestBody BookingRequest bookingRequest,
                                           @RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to add a new booking");
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            bookingRequest.setUserId(userId);
            bookingService.addNewBooking(bookingRequest);
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
