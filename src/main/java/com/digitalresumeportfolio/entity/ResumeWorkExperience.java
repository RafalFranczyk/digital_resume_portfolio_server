package com.digitalresumeportfolio.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "resume_work_experiences")
public class ResumeWorkExperience {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "work_title")
    private String workTitle;

    @Column(name = "work_description")
    private String workDescription;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    public ResumeWorkExperience() {

    }

    public ResumeWorkExperience(Date startDate, Date endDate, String companyName, String workTitle, String workDescription, Resume resume) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.companyName = companyName;
        this.workTitle = workTitle;
        this.workDescription = workDescription;
        this.resume = resume;
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

    public String getCompanyName() {
        return this.companyName;
    }

    public String getWorkTitle() {
        return this.workTitle;
    }

    public String getWorkDescription() {
        return this.workDescription;
    }

    public Resume getResume() {
        return this.resume;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }
}
