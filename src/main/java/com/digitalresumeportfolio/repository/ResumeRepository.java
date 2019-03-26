package com.digitalresumeportfolio.repository;

import com.digitalresumeportfolio.dao.Resume;
import com.digitalresumeportfolio.dao.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends CrudRepository<Resume, Long> {

    Optional<List<Resume>> findByUser(User user);

    @Override
    Optional<Resume> findById(Long id);

    @Override
    Resume save(Resume resume);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE user_resumes SET name = :name, description = :description WHERE id = :resumeId", nativeQuery = true)
    void updateResumeById(@Param("resumeId") Long resumeId, @Param("name") String name, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_resumes WHERE id = :resumeId", nativeQuery = true)
    void deleteResumeById(@Param("resumeId") Long resumeId);


}
