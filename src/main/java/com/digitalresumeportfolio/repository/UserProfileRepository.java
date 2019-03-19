package com.digitalresumeportfolio.repository;

import com.digitalresumeportfolio.entity.User;
import com.digitalresumeportfolio.entity.UserProfile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

    @Override
    UserProfile save(UserProfile userProfile);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user_profiles SET name = :name, surname = :surname, birth_date = :birthDate," +
            "phone_number = :phoneNumber, place_of_birth = :placeOfBirth, nationality = :nationality," +
            "city = :city, postal_code = :postalCode, address = :address, country = :country WHERE user_id = :userId ", nativeQuery = true)
    void updateUserDetails(@Param("name") String name, @Param("surname") String surname, @Param("birthDate") Date birthDate,
                           @Param("phoneNumber") String phoneNumber, @Param("placeOfBirth") String placeOfBirth,
                           @Param("nationality") String nationality, @Param("city") String city,
                           @Param("postalCode") String postalCode, @Param("address") String address,
                           @Param("country") String country, @Param("userId") Long userId);
}
