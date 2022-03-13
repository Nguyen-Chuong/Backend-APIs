package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Booking.UserBookingDetailDTO;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.repository.BookingDetailRepository;
import com.capstone_project.hbts.service.BookingDetailService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BookingDetailServiceImpl implements BookingDetailService {

    private final BookingDetailRepository bookingDetailRepository;

    private final ModelMapper modelMapper;

    public BookingDetailServiceImpl(BookingDetailRepository bookingDetailRepository,
                                    ModelMapper modelMapper) {
        this.bookingDetailRepository = bookingDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserBookingDetailDTO> getAllBookingDetail(int bookingId) {
        log.info("Request to get all booking detail by booking id");
        List<UserBookingDetail> bookingDetailList = bookingDetailRepository.getAllByBookingId(bookingId);

        List<UserBookingDetailDTO> userBookingDetailDTOList = bookingDetailList
                .stream()
                .map(item -> modelMapper.map(item, UserBookingDetailDTO.class))
                .collect(Collectors.toList());

        for(int i = 0; i < userBookingDetailDTOList.size(); i++){
            userBookingDetailDTOList.get(i).setRoomTypeId(bookingDetailList.get(i).getRoomType().getId());
        }

        return userBookingDetailDTOList;
    }

}
