package com.digitalresumeportfolio.repository;

import com.digitalresumeportfolio.dao.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole save(UserRole userRoles);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles where user_id = :userId", nativeQuery = true)
    void deleteUserRoleByUserId(@Param("userId") Long userId);
}
