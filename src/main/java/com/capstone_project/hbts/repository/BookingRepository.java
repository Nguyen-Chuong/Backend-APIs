package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.UserBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<UserBooking, Integer> {

    @Query(value = "SELECT u from UserBooking u WHERE u.users.id = :userId")
    List<UserBooking> findAllByUserId(@Param("userId") int userId);

    @Query(value = "SELECT u from UserBooking u WHERE u.reviewStatus = :reviewStatus and u.users.id = :userId")
    List<UserBooking> findBookingsReview(@Param("reviewStatus") int reviewStatus, @Param("userId") int userId);

    @Query(value = "SELECT u from UserBooking u WHERE u.hotel.id = :hotelId")
    List<UserBooking> findUserBookingByHotelId(@Param("hotelId") int hotelId);

    // when check vip status, only get number of booking that have been completed
    @Query(value = "SELECT count(u.id) from UserBooking u WHERE u.users.id = :userId and u.status = 2")
    int numberBookingCompleted(@Param("userId") int userId);

    @Query(value = "SELECT u from UserBooking u WHERE u.status = :status and u.users.id = :userId")
    List<UserBooking> findBookingsByStatus(@Param("status") int status, @Param("userId") int userId);

    @Query(value = "SELECT count(u.id) from UserBooking u")
    int getNumberOfBookingNoPaging();

    Page<UserBooking> findAllByOrderByBookingDateDesc(Pageable pageable);

    Page<UserBooking> findAllByHotel_IdOrderByBookingDateDesc(int hotelId, Pageable pageable);

    Page<UserBooking> findAllByHotel_IdAndStatusOrderByBookingDateDesc(int hotelId, int status, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.user_booking SET status = 3 WHERE id = :bookingId", nativeQuery = true)
    void cancelBooking(@Param("bookingId") int bookingId);

    @Query(value = "select * from heroku_4fe5c149618a3f9.user_booking where id = :id limit 1", nativeQuery = true)
    UserBooking getBookingById(@Param("id") int id);

    @Query(value = "select last_insert_id(id) from heroku_4fe5c149618a3f9.user_booking order by last_insert_id(id) desc limit 1;", nativeQuery = true)
    Integer getBookingIdJustInsert();

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.user_booking SET status = 2 WHERE id = :bookingId", nativeQuery = true)
    void completeBooking(@Param("bookingId") int bookingId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.user_booking SET type = :type WHERE id = :bookingId", nativeQuery = true)
    void updateBookingType(@Param("bookingId") int bookingId, @Param("type") int type);

    @Query(value = "select hotel_id from heroku_4fe5c149618a3f9.user_booking group by hotel_id order by count(id) desc limit :limit", nativeQuery = true)
    List<Integer> getTopHotHotelId(@Param("limit") int limit);

}
