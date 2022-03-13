package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users getUsersByUsername(String username);

    Users getUsersByEmail(String email);

    @Modifying
    @Query(value = "UPDATE capstone.users SET password = :newPass WHERE capstone.users.username = :username",
            nativeQuery = true)
    void changePass(
            @Param("username") String username,
            @Param("newPass") String newPass);

    @Query(value = "SELECT password from capstone.users WHERE capstone.users.username = :username",
            nativeQuery = true)
    String getOldPassword(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE capstone.users SET firstname = :firstName, lastname = :lastName, phone = :phone, " +
            "address = :address, avatar = :avatar, spend = :spend WHERE capstone.users.id = :id",
            nativeQuery = true)
    void updateUserProfile(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phone") String phone,
            @Param("address") String address,
            @Param("avatar") String avatar,
            @Param("spend") BigDecimal spend,
            @Param("id") Integer id);

    @Query(value = "select username from capstone.users where username = :username",
            nativeQuery = true)
    String getUsername(@Param("username") String username);

    @Query(value = "select email from capstone.users where email = :email",
            nativeQuery = true)
    String getEmail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE capstone.users SET id_vip = :idVip WHERE capstone.users.id = :userId",
            nativeQuery = true)
    void updateVipStatus(
            @Param("idVip") int idVip,
            @Param("userId") int userId);

    @Modifying
    @Query(value = "UPDATE capstone.users SET password = :newPass WHERE capstone.users.email = :email",
            nativeQuery = true)
    void changeForgotPassword(
            @Param("email") String email,
            @Param("newPass") String newPass);

    @Query(value = "SELECT * from capstone.users", nativeQuery = true)
    Page<Users> findAllUser(Pageable pageable);

    @Query(value = "SELECT * from capstone.users WHERE type = 1", nativeQuery = true)
    List<Users> findAllManager();

    @Modifying
    @Query(value = "UPDATE capstone.users SET type = 0 WHERE capstone.users.id = :userId",
            nativeQuery = true)
    void deleteManager(@Param("userId") int userId);

    @Query(value = "select id from capstone.users where username = :username limit 1",
            nativeQuery = true)
    int getUserId(@Param("username") String username);

    @Modifying
    @Query(value = "UPDATE capstone.users SET status = 0 WHERE capstone.users.id = :userId",
            nativeQuery = true)
    void deleteAccount(@Param("userId") int userId);

}
