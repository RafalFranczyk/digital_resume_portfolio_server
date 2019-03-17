package com.digitalresumeportfolio.repository;

import com.digitalresumeportfolio.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Override
    User save(User user);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users where id = :userId", nativeQuery = true)
    void deleteUserById(@Param("userId") Long userId);
}
