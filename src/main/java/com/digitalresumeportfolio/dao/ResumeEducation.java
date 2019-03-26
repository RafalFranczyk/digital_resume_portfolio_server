package com.digitalresumeportfolio.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "resume_educations")
public class ResumeEducation {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "to_present")
    private boolean toPresent;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    public ResumeEducation() {

    }

    public ResumeEducation(Date startDate, Date endDate, boolean toPresent, String schoolName, String title, String description, Resume resume) {
        this.startDate = startDate;
        this.schoolName = schoolName;
        this.title = title;
        this.description = description;
        this.resume = resume;
        this.toPresent = toPresent;
        if (!toPresent) {
            this.endDate = endDate;
        }
    }

    public Long getId() {
        return this.id;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isToPresent() {
        return this.toPresent;
    }

    public Resume getResume() {
        return this.resume;
    }


}
