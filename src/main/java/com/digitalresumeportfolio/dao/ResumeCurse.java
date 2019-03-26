package com.digitalresumeportfolio.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "resume_curses")
public class ResumeCurse {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "to_present")
    private boolean toPresent;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    public ResumeCurse() {

    }

    public ResumeCurse(String name, Date startDate, Date endDate, boolean toPresent, String description, Resume resume) {
        this.name = name;
        this.startDate = startDate;
        this.description = description;
        this.toPresent = toPresent;
        this.resume = resume;
        if (!toPresent) {
            this.endDate = endDate;
        }
    }

    public String getName() {
        return this.name;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public boolean isToPresent() {
        return this.toPresent;
    }

    public String getDescription() {
        return this.description;
    }

    public Resume getResume() {
        return this.resume;
    }

}
