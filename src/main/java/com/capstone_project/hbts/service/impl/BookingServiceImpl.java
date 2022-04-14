package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.booking.BookingListDTO;
import com.capstone_project.hbts.dto.booking.UserBookingDTO;
import com.capstone_project.hbts.entity.Hotel;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.entity.UserBooking;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.BookingDetailRepository;
import com.capstone_project.hbts.repository.BookingRepository;
import com.capstone_project.hbts.request.BookingDetailRequest;
import com.capstone_project.hbts.request.BookingRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ModelMapper modelMapper;

    private final BookingDetailRepository bookingDetailRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ModelMapper modelMapper, BookingDetailRepository bookingDetailRepository) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
        this.bookingDetailRepository = bookingDetailRepository;
    }

    public BigDecimal countTotalPaidForABooking(UserBooking userBooking){
        // get % discount VIP travesily
        int discount = userBooking.getUsers().getVip().getDiscount();
        // get number day
        long numberDayBooking = ChronoUnit.DAYS.between(userBooking.getCheckIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                userBooking.getCheckOut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        // get list user booking detail
        Set<UserBookingDetail> userBookingDetails = userBooking.getListUserBookingDetail();
        BigDecimal totalPaid = BigDecimal.valueOf(0);
        for(UserBookingDetail item : userBookingDetails){
            totalPaid = totalPaid.add(item.getPaid().multiply(BigDecimal.valueOf(item.getQuantity()))
                    .multiply(BigDecimal.valueOf(numberDayBooking)));
        }
        // count total paid discounted by travesily VIP
        BigDecimal totalPaidDiscountedVIP = totalPaid.multiply(BigDecimal.valueOf(1 - (double) discount/100));
        // get tax
        double tax = (double) userBooking.getHotel().getTaxPercentage() / 100;
        return totalPaidDiscountedVIP.multiply(BigDecimal.valueOf(1 + tax));
    }

    @Override
    public List<UserBookingDTO> getAllBookings(int userId) {
        List<UserBooking> list = bookingRepository.findAllByUserId(userId);
        List<UserBookingDTO> userBookingDTOList = list.stream().map(item -> modelMapper.map(item, UserBookingDTO.class))
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
        List<UserBooking> list = bookingRepository.findBookingsReview(reviewStatus, userId);
        List<UserBookingDTO> userBookingDTOList = list.stream().map(item -> modelMapper.map(item, UserBookingDTO.class))
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
        return bookingRepository.numberBookingCompleted(userId);
    }

    @Override
    public List<UserBookingDTO> getAllBookingsByStatus(int status, int userId) {
        List<UserBooking> list = bookingRepository.findBookingsByStatus(status, userId);
        List<UserBookingDTO> userBookingDTOList = list.stream().map(item -> modelMapper.map(item, UserBookingDTO.class))
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
        Page<UserBooking> userBookingPage = bookingRepository.findAllByOrderByBookingDateDesc(pageable);
        List<UserBooking> userBookingList = userBookingPage.getContent();
        List<BookingListDTO> listBookingDTOList = userBookingList.stream().map(item -> modelMapper.map(item, BookingListDTO.class))
                .collect(Collectors.toList());
        for(int i = 0; i < listBookingDTOList.size(); i++){
            listBookingDTOList.get(i).setUsername(userBookingList.get(i).getUsers().getUsername());
        }
        return new CustomPageImpl<>(listBookingDTOList);
    }

    @Override
    public int getNumberOfBookingForAdmin() {
        return bookingRepository.getNumberOfBookingNoPaging();
    }

    @Override
    public UserBookingDTO getBookingById(int bookingId) {
        UserBooking userBooking = bookingRepository.getBookingById(bookingId);
        UserBookingDTO userBookingDTO = modelMapper.map(userBooking, UserBookingDTO.class);
        userBookingDTO.setTotalPaid(countTotalPaidForABooking(userBooking));
        return userBookingDTO;
    }

    @Override
    public Page<UserBookingDTO> getBookingsByHotelId(int hotelId, int status,Pageable pageable) {
        List<UserBookingDTO> userBookingDTOList;
        List<UserBooking> resultList;
        if(status == 0){
            resultList = bookingRepository.findAllByHotel_IdOrderByBookingDateDesc(hotelId, pageable).getContent();
        } else {
            resultList = bookingRepository.findAllByHotel_IdAndStatusOrderByBookingDateDesc(hotelId, status, pageable).getContent();
        }
        userBookingDTOList = resultList.stream().map(item -> modelMapper.map(item, UserBookingDTO.class))
                .collect(Collectors.toList());
        for(int i = 0 ; i < resultList.size(); i++){
            BigDecimal totalPaid = countTotalPaidForABooking(resultList.get(i));
            // set total paid for each user booking
            userBookingDTOList.get(i).setTotalPaid(totalPaid);
            // set email
            userBookingDTOList.get(i).setEmail(resultList.get(i).getUsers().getEmail());
        }
        return new CustomPageImpl<>(userBookingDTOList);
    }

    @Transactional
    @Override
    public void cancelBooking(int bookingId) {
        bookingRepository.cancelBooking(bookingId);
    }

    @Transactional
    @Override
    public Integer addNewBooking(BookingRequest bookingRequest) {
        // set current time stamp
        bookingRequest.setBookingDate(new Timestamp(System.currentTimeMillis()));
        // set review status is 0; after review reset 1
        bookingRequest.setReviewStatus(0);
        // set booking status is 1-upcoming, user cancel booking -> set to 3
        // completed a booking -> set to 2 - call when they paid money or hotel confirm
        bookingRequest.setStatus(1);
        UserBooking userBooking = modelMapper.map(bookingRequest, UserBooking.class);
        // set hotel, user
        Hotel hotel = new Hotel();
        hotel.setId(bookingRequest.getHotelId());
        Users users = new Users();
        users.setId(bookingRequest.getUserId());
        userBooking.setHotel(hotel);
        userBooking.setUsers(users);
        bookingRepository.save(userBooking);

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
            UserBooking userBookingAddForDetail = new UserBooking();
            userBookingAddForDetail.setId(bookingId);
            userBookingDetail.setUserBooking(userBookingAddForDetail);
            // add all of them to list
            listBookingDetailToAdd.add(userBookingDetail);
        }
        // add all to db using batch processing
        bookingDetailRepository.saveAll(listBookingDetailToAdd);
        return bookingId;
    }

    @Transactional
    @Override
    public void completeBooking(int bookingId) {
        bookingRepository.completeBooking(bookingId);
    }

    @Transactional
    @Override
    public void updateBookingType(int bookingId, int type) {
        bookingRepository.updateBookingType(bookingId, type);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateBookingStatusWhenUpComingOverDate(){
        // get list all booking up coming
        List<UserBooking> listBookingUpComing = bookingRepository.findAll().stream().filter(item -> item.getStatus() == 1).collect(Collectors.toList());
        // new list booking need to update status
        List<UserBooking> bookingNeedToUpdate = new ArrayList<>();
        for(UserBooking userBooking : listBookingUpComing){
            if(userBooking.getCheckIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now(ZoneId.systemDefault()))){
                // cancel it
                userBooking.setStatus(3);
                bookingNeedToUpdate.add(userBooking);
            }
        }
        // batch processing
        bookingRepository.saveAll(bookingNeedToUpdate);
    }

}
