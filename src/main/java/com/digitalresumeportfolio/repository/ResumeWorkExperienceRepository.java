package com.digitalresumeportfolio.repository;

import com.digitalresumeportfolio.entity.Resume;
import com.digitalresumeportfolio.entity.ResumeWorkExperience;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ResumeWorkExperienceRepository extends CrudRepository<ResumeWorkExperience, Long> {

    Optional<List<ResumeWorkExperience>> findByResume(Resume resume);

    @Override
    Optional<ResumeWorkExperience> findById(Long id);

    @Override
    ResumeWorkExperience save(ResumeWorkExperience resumeWorkExperience);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE resume_work_experiences SET start_date = :startDate, end_date = :endDate, company_name = :companyName," +
            "work_title = :workTitle, work_description = :workDescription WHERE id = :resumeWorkExperienceId",
            nativeQuery = true)
    void updateResumeWorkExperienceById(@Param("resumeWorkExperienceId") Long resumeWorkExperienceId,
                                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                        @Param("companyName") String companyName, @Param("workTitle") String workTitle,
                                        @Param("workDescription") String workDescription);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM resume_work_experiences WHERE id = :resumeWorkExperienceId", nativeQuery = true)
    void deleteResumeWorkExperienceById(@Param("resumeWorkExperienceId") Long resumeWorkExperienceId);
}
