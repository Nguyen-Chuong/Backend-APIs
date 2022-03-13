package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Request.RequestDTO;
import com.capstone_project.hbts.request.PostHotelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    /**
     * add a request to post hotel for provider
     * @param postHotelRequest
     * @return
     */
    void addNewRequest(PostHotelRequest postHotelRequest);

    /**
     * for admin to accept a request
     * @param requestId
     * @return
     */
    void acceptRequest(int requestId);

    /**
     * for admin to deny a request
     * @param requestId
     * @return
     */
    void denyRequest(int requestId);

    /**
     * for admin to view all requests by status
     * @param status
     * @param pageable
     * @return
     */
    Page<RequestDTO> viewAllRequestByStatus(int status, Pageable pageable);

    /**
     * to check if a hotel can request or not
     * @param hotelId
     * @return
     */
    boolean checkRequest(int hotelId);

    /**
     * to view all request of provider
     * @param providerId
     * @return
     */
    List<RequestDTO> getRequestByProviderId(int providerId);

    /**
     * to view request status
     * @param requestId
     * @return
     */
    Integer getRequestStatus(int requestId);

    /**
     * for provider to cancel a request
     * @param requestId
     * @return
     */
    void cancelRequest(int requestId);

}
