package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.booking.UserBookingDetailDTO;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.repository.BookingDetailRepository;
import com.capstone_project.hbts.service.BookingDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    private final BookingDetailRepository bookingDetailRepository;

    private final ModelMapper modelMapper;

    public BookingDetailServiceImpl(BookingDetailRepository bookingDetailRepository, ModelMapper modelMapper) {
        this.bookingDetailRepository = bookingDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
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
