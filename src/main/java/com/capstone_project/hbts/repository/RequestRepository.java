package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Modifying
    @Query(value = "UPDATE Request r set r.status = 2 where r.id = :requestId")
    void acceptRequest(@Param("requestId") int requestId);

    @Query(value = "select * from heroku_4fe5c149618a3f9.request where id = :requestId limit 1", nativeQuery = true)
    Request getRequestById(@Param("requestId") int requestId);

    @Modifying
    @Query(value = "UPDATE Request r set r.status = 3 where r.id = :requestId")
    void denyRequest(@Param("requestId") int requestId);

    @Query(value = "select r from Request r where r.status = :status ORDER BY r.requestDate DESC")
    Page<Request> getAllRequestByStatus(@Param("status") int status, Pageable pageable);

    @Query(value = "select count(r.id) from Request r where r.status = :status")
    int getNumberOfRequestNoPaging(@Param("status") int status);

    Page<Request> findAllByOrderByRequestDateDesc(Pageable pageable);

    @Query(value = "select r.status from Request r where r.hotel.id = :hotelId")
    List<Integer> getRequestStatusByHotelId(@Param("hotelId") int hotelId);

    List<Request> getAllByProviderIdOrderByRequestDateDesc(int providerId);

    @Query(value = "SELECT r.status FROM Request r WHERE r.id = :requestId")
    Integer viewRequestStatus(@Param("requestId") int requestId);

    @Modifying
    @Query(value = "UPDATE Request r set r.status = 4 where r.id = :requestId")
    void cancelRequest(@Param("requestId") int requestId);

}
