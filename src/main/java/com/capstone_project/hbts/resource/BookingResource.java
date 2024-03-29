package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.constant.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.booking.BookingListDTO;
import com.capstone_project.hbts.dto.booking.UserBookingDTO;
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

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class BookingResource {

    private final BookingService bookingService;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final DataDecryption dataDecryption;

    public BookingResource(BookingService bookingService, UserService userService, JwtTokenUtil jwtTokenUtil, DataDecryption dataDecryption) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote both admin & user can call this
     */
    @GetMapping("/user-bookings")
    public ResponseEntity<?> getUserBooking(@RequestParam String username) {
        log.info("REST request to get list user's booking by username");
        String usernameDecrypted;
        try {
            usernameDecrypted = dataDecryption.convertEncryptedDataToString(username);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        int userId = userService.getUserId(usernameDecrypted);
        try {
            List<UserBookingDTO> userBookingDTOList = bookingService.getAllBookings(userId);
            return ResponseEntity.ok().body(new ApiResponse<>(200, userBookingDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote get bookings need to review or not
     */
    @GetMapping("/bookings-review/{reviewStatus}")
    public ResponseEntity<?> getUserBookingReview(@PathVariable int reviewStatus, @RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get list user's booking need to review or not");
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            List<UserBookingDTO> userBookingDTOList = bookingService.getAllBookingsReview(reviewStatus, userId);
            return ResponseEntity.ok().body(new ApiResponse<>(200, userBookingDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote to get number booking completed
     */
    @GetMapping("/bookings-completed")
    public ResponseEntity<?> getNumberBookingsCompleted(@RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get number booking completed by user id");
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            int numberBookingCompleted = bookingService.getNumberBookingsCompleted(userId);
            return ResponseEntity.ok().body(new ApiResponse<>(200, numberBookingCompleted, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote get list booking by status
     */
    @GetMapping("/bookings-by-status/{status}")
    public ResponseEntity<?> getUserBookingByStatus(@PathVariable int status, @RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get list user's booking by status");
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            List<UserBookingDTO> userBookingDTOList = bookingService.getAllBookingsByStatus(status, userId);
            return ResponseEntity.ok().body(new ApiResponse<>(200, userBookingDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote only for admin/manager to get all bookings
     */
    @GetMapping("/get-all-booking")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllUserBooking(@RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                               @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to get list user's booking for admin");
        try {
            int totalNumberOfBooking = bookingService.getNumberOfBookingForAdmin();
            Page<BookingListDTO> userBookingDTOList = bookingService.getAllBookingForAdmin(PageRequest.of(page, pageSize));
            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(userBookingDTOList.getContent(),
                    totalNumberOfBooking, page, userBookingDTOList.getSize());
            return ResponseEntity.ok().body(new ApiResponse<>(200, dataPagingResponse, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote get booking by id
     */
    @GetMapping("/booking")
    public ResponseEntity<?> getBookingById(@RequestParam String bookingId) {
        log.info("REST request to get user's booking by id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            UserBookingDTO userBookingDTO = bookingService.getBookingById(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, userBookingDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider to view list booking in their hotel
     */
    @GetMapping("/bookings/hotel")
    public ResponseEntity<?> getBookingByHotelId(@RequestParam String hotelId, @RequestParam int status,
                                                 @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                                 @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to get user's booking by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            Page<UserBookingDTO> userBookingDTOPage = bookingService.getBookingsByHotelId(id, status, PageRequest.of(page, pageSize));
            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(userBookingDTOPage.getContent(),
                    userBookingDTOPage.getTotalElements(), page, userBookingDTOPage.getSize());
            return ResponseEntity.ok().body(new ApiResponse<>(200, dataPagingResponse, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for user to cancel booking
     */
    @PatchMapping("/cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestParam String bookingId) {
        log.info("REST request to cancel booking");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for user to add a new booking
     */
    @PostMapping("/add-booking")
    public ResponseEntity<?> addNewBooking(@RequestBody BookingRequest bookingRequest, @RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to add a new booking");
        int numberItemBooked = bookingRequest.getBookingDetail().size();
        // number item booking > 2, cannot accept
        if (numberItemBooked > 2) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_BOOK_001_LABEL));
        }
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            bookingRequest.setUserId(userId);
            int bookingId = bookingService.addNewBooking(bookingRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, bookingId, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for user to complete booking, called when user complete transaction
     */
    @PatchMapping("/complete-booking")
    public ResponseEntity<?> completeBooking(@RequestParam String bookingId) {
        log.info("REST request to complete booking");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            bookingService.completeBooking(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for changing type booking, 1 - cod, 2 - payment
     */
    @PatchMapping("/update-booking-type")
    public ResponseEntity<?> updateBookingType(@RequestParam String bookingId, @RequestParam int type) {
        log.info("REST request to update booking type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            bookingService.updateBookingType(id, type);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
