package com.digitalresumeportfolio.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_resumes")
public class Resume {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeWorkExperience> resumeWorkExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeCurse> resumeCurses = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeDetail> resumeDetails = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeEducation> resumeEducations = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeHobby> resumeHobbies = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeLanguage> resumeLanguages = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<ResumeSkill> resumeSkills = new ArrayList<>();

    public Resume() {

    }

    public Resume(User user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResumeWorkExperience> getResumeWorkExperiences() {
        return this.resumeWorkExperiences;
    }

    public List<ResumeCurse> getResumeCurses() {
        return this.resumeCurses;
    }

    public List<ResumeEducation> getResumeEducations() {
        return this.resumeEducations;
    }

    public List<ResumeHobby> getResumeHobbies(){
        return this.resumeHobbies;
    }

    public List<ResumeLanguage> getResumeLanguages() {
        return this.resumeLanguages;
    }

    public List<ResumeSkill> getResumeSkills() {
        return this.resumeSkills;
    }
}
