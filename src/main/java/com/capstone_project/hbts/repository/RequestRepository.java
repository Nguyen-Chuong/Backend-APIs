package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Modifying
    @Query(value = "insert into capstone.request(request_date, status, hotel_id, provider_id) " +
            "values (:requestDate, :status, :hotelId, :providerId)",
            nativeQuery = true)
    void addNewRequest(
            @Param("requestDate") Timestamp requestDate,
            @Param("status") int status,
            @Param("hotelId") int hotelId,
            @Param("providerId") int providerId);

    @Modifying
    @Query(value = "UPDATE capstone.request set status = 2 where id = :requestId",
            nativeQuery = true)
    void acceptRequest(@Param("requestId") int requestId);

    @Query(value = "select * from capstone.request where id = :requestId limit 1",
            nativeQuery = true)
    Request getRequestById(@Param("requestId") int requestId);

    @Modifying
    @Query(value = "UPDATE capstone.request set status = 3 where id = :requestId",
            nativeQuery = true)
    void denyRequest(@Param("requestId") int requestId);

    @Query(value = "select * from capstone.request where status = :status ORDER BY request_date DESC",
            nativeQuery = true)
    Page<Request> getAllRequestByStatus(@Param("status") int status, Pageable pageable);

    Page<Request> findAllByOrderByRequestDateDesc(Pageable pageable);

    @Query(value = "select status from capstone.request where hotel_id = :hotelId",
            nativeQuery = true)
    List<Integer> getRequestStatusByHotelId(@Param("hotelId") int hotelId);

    List<Request> getAllByProviderIdOrderByRequestDateDesc(int providerId);

    @Query(value = "SELECT status FROM capstone.request WHERE id = :requestId",
            nativeQuery = true)
    Integer viewRequestStatus(@Param("requestId") int requestId);

    @Modifying
    @Query(value = "UPDATE capstone.request set status = 4 where id = :requestId",
            nativeQuery = true)
    void cancelRequest(@Param("requestId") int requestId);

}
