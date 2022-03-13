package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

    Provider getProviderByUsername(String username);

    Provider getProviderByEmail(String email);

    @Query(value = "select username from capstone.provider where username = :username",
            nativeQuery = true)
    String getUsername(@Param("username") String username);

    @Query(value = "select email from capstone.provider where email = :email",
            nativeQuery = true)
    String getEmail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE capstone.provider SET provider_name = :providerName, phone = :phone, " +
            "address = :address WHERE capstone.provider.id = :id",
            nativeQuery = true)
    void updateProviderProfile(
            @Param("providerName") String providerName,
            @Param("phone") String phone,
            @Param("address") String address,
            @Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE capstone.provider SET password = :newPass WHERE capstone.provider.username = :username",
            nativeQuery = true)
    void changePass(
            @Param("username") String username,
            @Param("newPass") String newPass);

    @Query(value = "SELECT password from capstone.provider WHERE capstone.provider.username = :username",
            nativeQuery = true)
    String getOldPassword(@Param("username") String username);

    @Query(value = "SELECT * from capstone.provider", nativeQuery = true)
    Page<Provider> findAllProvider(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE capstone.provider set status = 0 WHERE id = :providerId",
            nativeQuery = true)
    void banProviderById(@Param("providerId") int providerId);

}
