package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.request.RequestDTO;
import com.capstone_project.hbts.request.PostHotelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    /**
     * add a request to post hotel for provider
     */
    void addNewRequest(PostHotelRequest postHotelRequest);

    /**
     * for admin to accept a request
     */
    void acceptRequest(int requestId);

    /**
     * for admin to deny a request
     */
    void denyRequest(int requestId);

    /**
     * for admin to view all requests by status
     */
    Page<RequestDTO> viewAllRequestByStatus(int status, Pageable pageable);

    /**
     * to check if a hotel can request or not
     */
    boolean checkRequest(int hotelId);

    /**
     * to view all request of provider
     */
    List<RequestDTO> getRequestByProviderId(int providerId);

    /**
     * to view request status
     */
    Integer getRequestStatus(int requestId);

    /**
     * for provider to cancel a request
     */
    void cancelRequest(int requestId);

}
