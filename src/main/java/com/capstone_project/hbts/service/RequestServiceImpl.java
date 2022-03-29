package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Request.RequestDTO;
import com.capstone_project.hbts.entity.Request;
import com.capstone_project.hbts.repository.HotelRepository;
import com.capstone_project.hbts.repository.RequestRepository;
import com.capstone_project.hbts.request.PostHotelRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl {

    private final RequestRepository requestRepository;

    private final HotelRepository hotelRepository;

    private final ModelMapper modelMapper;

    public RequestServiceImpl(RequestRepository requestRepository, HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void addNewRequest(PostHotelRequest postHotelRequest) {
        // get current timestamp
        postHotelRequest.setRequestDate(new Timestamp(System.currentTimeMillis()));
        // set status to 1-pending, await admin to process
        postHotelRequest.setStatus(1);
        requestRepository.addNewRequest(postHotelRequest.getRequestDate(), postHotelRequest.getStatus(),
                postHotelRequest.getHotelId(), postHotelRequest.getProviderId());
    }

    @Transactional
    public void acceptRequest(int requestId) {
        // update request status to 2 - approved
        requestRepository.acceptRequest(requestId);
        // get hotel id to accept, ready to on stage
        int hotelId = requestRepository.getRequestById(requestId).getHotel().getId();
        // update hotel status
        hotelRepository.updateHotelStatus(hotelId, 1);
    }

    @Transactional
    public void denyRequest(int requestId) {
        // update request status to 2 - approved
        requestRepository.denyRequest(requestId);
        // get hotel id to deny
        int hotelId = requestRepository.getRequestById(requestId).getHotel().getId();
        // delete this hotel
        hotelRepository.updateHotelStatus(hotelId, 5);
    }

    public Page<RequestDTO> viewAllRequestByStatus(int status, Pageable pageable) {
        // 1-pending, 2-accepted, 3-denied, 4-cancelled
        // status = 0 -> get all
        if(status == 0){
            // get page
            Page<Request> requestPage = requestRepository.findAllByOrderByRequestDateDesc(pageable);
            // convert to list dto
            List<RequestDTO> requestDTOList = requestPage.getContent().stream()
                    .map(item -> modelMapper.map(item, RequestDTO.class)).collect(Collectors.toList());
            // convert to paging
            return new CustomPageImpl<>(requestDTOList);
        } else {  // 1-pending, 2-accepted, 3-denied, 4-cancelled
            // get page
            Page<Request> requestPage = requestRepository.getAllRequestByStatus(status, pageable);
            // convert to list dto
            List<RequestDTO> requestDTOList = requestPage.getContent().stream()
                    .map(item -> modelMapper.map(item, RequestDTO.class)).collect(Collectors.toList());
            // convert to paging
            return new CustomPageImpl<>(requestDTOList);
        }
    }

    public boolean checkRequest(int hotelId) {
        List<Integer> listStatus = requestRepository.getRequestStatusByHotelId(hotelId);
        // contain any request pending or accepted -> cannot request again
        return !listStatus.contains(1) && !listStatus.contains(2);
        // else denied-3, hotel can request again
    }

    public List<RequestDTO> getRequestByProviderId(int providerId) {
        return requestRepository.getAllByProviderIdOrderByRequestDateDesc(providerId).stream()
                .map(item -> modelMapper.map(item, RequestDTO.class)).collect(Collectors.toList());
    }

    public Integer getRequestStatus(int requestId) {
        return requestRepository.viewRequestStatus(requestId);
    }

    @Transactional
    public void cancelRequest(int requestId) {
        requestRepository.cancelRequest(requestId);
    }

}
