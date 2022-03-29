package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Booking.UserBookingDetailDTO;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.repository.BookingDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingDetailServiceImpl {

    private final BookingDetailRepository bookingDetailRepository;

    private final ModelMapper modelMapper;

    public BookingDetailServiceImpl(BookingDetailRepository bookingDetailRepository, ModelMapper modelMapper) {
        this.bookingDetailRepository = bookingDetailRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserBookingDetailDTO> getAllBookingDetail(int bookingId) {
        List<UserBookingDetail> bookingDetailList = bookingDetailRepository.getAllByBookingId(bookingId);
        List<UserBookingDetailDTO> userBookingDetailDTOList = bookingDetailList.stream()
                .map(item -> modelMapper.map(item, UserBookingDetailDTO.class)).collect(Collectors.toList());
        for(int i = 0; i < userBookingDetailDTOList.size(); i++){
            userBookingDetailDTOList.get(i).setRoomTypeId(bookingDetailList.get(i).getRoomType().getId());
        }
        return userBookingDetailDTOList;
    }

}
