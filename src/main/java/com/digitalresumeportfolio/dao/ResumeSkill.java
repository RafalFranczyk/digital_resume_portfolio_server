package com.digitalresumeportfolio.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "resume_skills")
public class ResumeSkill {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private double level;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    public ResumeSkill() {

    }

    public ResumeSkill(String name, double level, Resume resume) {
        this.name = name;
        this.level = level;
        this.resume = resume;
    }

    public String getName() {
        return this.name;
    }

    public double getLevel() {
        return this.level;
    }

    public Resume getResume() {
        return this.resume;
    }


}
