package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users getUsersByUsername(String username);

    Users getUsersByEmail(String email);

    @Modifying
    @Query(value = "UPDATE Users u SET u.password = :newPass WHERE u.username = :username")
    void changePass(@Param("username") String username, @Param("newPass") String newPass);

    @Query(value = "SELECT u.password from Users u WHERE u.username = :username")
    String getOldPassword(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.users SET firstname = :firstName, lastname = :lastName, phone = :phone, " +
            "address = :address, avatar = :avatar, spend = :spend WHERE heroku_4fe5c149618a3f9.users.id = :id", nativeQuery = true)
    void updateUserProfile(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone,
                           @Param("address") String address, @Param("avatar") String avatar, @Param("spend") BigDecimal spend,
                           @Param("id") Integer id);

    @Query(value = "select u.username from Users u where u.username = :username")
    String getUsername(@Param("username") String username);

    @Query(value = "select u.email from Users u where u.email = :email")
    String getEmail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE Users u SET u.vip.id = :idVip WHERE u.id = :userId")
    void updateVipStatus(@Param("idVip") int idVip, @Param("userId") int userId);

    @Modifying
    @Query(value = "UPDATE Users u SET u.password = :newPass WHERE u.email = :email")
    void changeForgotPassword(@Param("email") String email, @Param("newPass") String newPass);

    @Query(value = "SELECT u from Users u WHERE u.type = 0")
    Page<Users> findAllUser(Pageable pageable);

    @Query(value = "SELECT u from Users u WHERE u.type = 1")
    List<Users> findAllManager();

    @Modifying
    @Query(value = "UPDATE Users u SET u.type = 0 WHERE u.id = :userId")
    void deleteManager(@Param("userId") int userId);

    @Query(value = "select count(u.id) from Users u where u.status = 1 and u.type = 0")
    int getNumberOfUserActive();

    @Query(value = "select id from heroku_4fe5c149618a3f9.users where username = :username limit 1", nativeQuery = true)
    int getUserId(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE Users u SET u.status = 0 WHERE u.id = :userId")
    void deleteAccount(@Param("userId") int userId);

}
