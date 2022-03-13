package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Booking.BookingListDTO;
import com.capstone_project.hbts.dto.Booking.UserBookingDTO;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.entity.UserBooking;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.repository.BookingDetailRepository;
import com.capstone_project.hbts.repository.BookingRepository;
import com.capstone_project.hbts.request.BookingDetailRequest;
import com.capstone_project.hbts.request.BookingRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.BookingService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ModelMapper modelMapper;

    private final BookingDetailRepository bookingDetailRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ModelMapper modelMapper,
                              BookingDetailRepository bookingDetailRepository) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
        this.bookingDetailRepository = bookingDetailRepository;
    }

    public BigDecimal countTotalPaidForABooking(UserBooking userBooking){
        // get list user booking detail
        Set<UserBookingDetail> userBookingDetails = userBooking.getListUserBookingDetail();

        BigDecimal totalPaid = BigDecimal.valueOf(0);

        for(UserBookingDetail item : userBookingDetails){
            totalPaid = totalPaid
                    .add(item.getPaid() // price for each room type
                            .multiply(BigDecimal.valueOf(item.getQuantity()))); // quantity
        }
        return totalPaid;
    }

    @Override
    public List<UserBookingDTO> getAllBookings(int userId) {
        log.info("Request to get all booking by user id");
        List<UserBooking> list = bookingRepository.findAllByUserId(userId);

        List<UserBookingDTO> userBookingDTOList = list.stream()
                .map(item -> modelMapper.map(item, UserBookingDTO.class))
                .collect(Collectors.toList());

        for(int i = 0 ; i < list.size(); i++){
            BigDecimal totalPaid = countTotalPaidForABooking(list.get(i));
            // set total paid for each user booking
            userBookingDTOList.get(i).setTotalPaid(totalPaid);
        }
        return userBookingDTOList;
    }

    @Override
    public List<UserBookingDTO> getAllBookingsReview(int reviewStatus, int userId) {
        log.info("Request to get all booking need to review or not by user id");
        List<UserBooking> list = bookingRepository.findBookingsReview(reviewStatus, userId);

        List<UserBookingDTO> userBookingDTOList = list.stream()
                .map(item -> modelMapper.map(item, UserBookingDTO.class))
                .collect(Collectors.toList());

        for(int i = 0 ; i < list.size(); i++){
            BigDecimal totalPaid = countTotalPaidForABooking(list.get(i));
            // set total paid for each user booking
            userBookingDTOList.get(i).setTotalPaid(totalPaid);
        }
        return userBookingDTOList;
    }

    @Override
    public int getNumberBookingsCompleted(int userId) {
        log.info("Request to get number booking completed by user id");
        return bookingRepository.numberBookingCompleted(userId);
    }

    @Override
    public List<UserBookingDTO> getAllBookingsByStatus(int status, int userId) {
        log.info("Request to get all booking by status");
        List<UserBooking> list = bookingRepository.findBookingsByStatus(status, userId);

        List<UserBookingDTO> userBookingDTOList = list.stream()
                .map(item -> modelMapper.map(item, UserBookingDTO.class))
                .collect(Collectors.toList());

        for(int i = 0 ; i < list.size(); i++){
            BigDecimal totalPaid = countTotalPaidForABooking(list.get(i));
            // set total paid for each user booking
            userBookingDTOList.get(i).setTotalPaid(totalPaid);
        }
        return userBookingDTOList;
    }

    @Override
    public Page<BookingListDTO> getAllBookingForAdmin(Pageable pageable) {
        log.info("Request to get all booking for admin paging");
        Page<UserBooking> userBookingPage = bookingRepository.findAllByOrderByBookingDateDesc(pageable);

        List<UserBooking> userBookingList = userBookingPage.getContent();

        List<BookingListDTO> listBookingDTOList = userBookingList.stream()
                .map(item -> modelMapper.map(item, BookingListDTO.class))
                .collect(Collectors.toList());

        for(int i = 0; i < listBookingDTOList.size(); i++){
            listBookingDTOList.get(i).setUsername(userBookingList.get(i).getUsers().getUsername());
        }

        return new CustomPageImpl<>(listBookingDTOList);
    }

    @Override
    public UserBookingDTO getBookingById(int bookingId) {
        log.info("Request to get booking by id");
        UserBooking userBooking = bookingRepository.getBookingById(bookingId);
        UserBookingDTO userBookingDTO = modelMapper.map(userBooking, UserBookingDTO.class);
        userBookingDTO.setTotalPaid(countTotalPaidForABooking(userBooking));
        return userBookingDTO;
    }

    @Override
    public Page<UserBookingDTO> getBookingsByHotelId(int hotelId, Pageable pageable) {
        log.info("Request to get booking by hotel id");
        Page<UserBooking> userBookingPage = bookingRepository.findAllByHotel_IdOrderByBookingDateDesc(hotelId, pageable);

        List<UserBooking> list = userBookingPage.getContent();

        List<UserBookingDTO> userBookingDTOList = list
                .stream()
                .map(item -> modelMapper.map(item, UserBookingDTO.class))
                .collect(Collectors.toList());

        for(int i = 0 ; i < list.size(); i++){
            BigDecimal totalPaid = countTotalPaidForABooking(list.get(i));
            // set total paid for each user booking
            userBookingDTOList.get(i).setTotalPaid(totalPaid);
        }

        return new CustomPageImpl<>(userBookingDTOList);
    }

    @Override
    @Transactional
    public void cancelBooking(int bookingId) {
        log.info("Request to cancel booking");
        bookingRepository.cancelBooking(bookingId);
    }

    @Override
    @Transactional
    public void addNewBooking(BookingRequest bookingRequest) {
        log.info("Request to add a new booking");
        // set current time stamp
        bookingRequest.setBookingDate(new Timestamp(System.currentTimeMillis()));
        // set review status is 0; after review reset 1
        bookingRequest.setReviewStatus(0);
        // set booking status is 1-upcoming, user can cancel booking -> set to 3
        // completed a booking -> set to 2 - may call when they paid money or over checkin date
        bookingRequest.setStatus(1);
        // save to db
        bookingRepository.addNewBooking(bookingRequest.getBookedQuantity(),
                bookingRequest.getBookingDate(),
                bookingRequest.getCheckIn(),
                bookingRequest.getCheckOut(),
                bookingRequest.getReviewStatus(),
                bookingRequest.getStatus(),
                bookingRequest.getHotelId(),
                bookingRequest.getUserId());
        // get booking that just insert to db
        Integer bookingId = bookingRepository.getBookingIdJustInsert();
        // new list user booking detail for adding
        List<UserBookingDetail> listBookingDetailToAdd = new ArrayList<>();
        // get list booking detail request
        List<BookingDetailRequest> list = bookingRequest.getBookingDetail();
        // loop through collection
        for (BookingDetailRequest bookingDetailRequest : list) {
            // new a booking detail
            UserBookingDetail userBookingDetail = new UserBookingDetail();
            // set paid
            userBookingDetail.setPaid(bookingDetailRequest.getPaid());
            // set quantity
            userBookingDetail.setQuantity(bookingDetailRequest.getQuantity());
            // set room type
            RoomType roomType = new RoomType();
            roomType.setId(bookingDetailRequest.getRoomTypeId());
            userBookingDetail.setRoomType(roomType);
            // set user booking
            UserBooking userBooking = new UserBooking();
            userBooking.setId(bookingId);
            userBookingDetail.setUserBooking(userBooking);
            // add all of them to list
            listBookingDetailToAdd.add(userBookingDetail);
        }
        // add all to db using batch processing
        bookingDetailRepository.saveAll(listBookingDetailToAdd);
    }

}
