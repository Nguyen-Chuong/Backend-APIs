package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

    Provider getProviderByUsername(String username);

    Provider getProviderByEmail(String email);

    @Query(value = "select p.username from Provider p where p.username = :username")
    String getUsername(@Param("username") String username);

    @Query(value = "select p.email from Provider p where p.email = :email")
    String getEmail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.provider SET provider_name = :providerName, phone = :phone, address = :address" +
            " WHERE heroku_4fe5c149618a3f9.provider.id = :id", nativeQuery = true)
    void updateProviderProfile(@Param("providerName") String providerName, @Param("phone") String phone,
                               @Param("address") String address, @Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Provider p SET p.password = :newPass WHERE p.username = :username")
    void changePass(@Param("username") String username, @Param("newPass") String newPass);

    @Query(value = "SELECT p.password from Provider p WHERE p.username = :username")
    String getOldPassword(@Param("username") String username);

    @Query(value = "SELECT p from Provider p where p.status = :status")
    Page<Provider> findAllProvider(@Param("status") int status, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Provider p set p.status = 0 WHERE p.id = :providerId")
    void banProviderById(@Param("providerId") int providerId);

    @Modifying
    @Query(value = "UPDATE Provider p SET p.password = :newPass WHERE p.email = :email")
    void changeProviderForgotPassword(@Param("email") String email, @Param("newPass") String newPass);

}
