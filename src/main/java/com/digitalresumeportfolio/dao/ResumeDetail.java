package com.digitalresumeportfolio.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "resume_details")
public class ResumeDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "github_link")
    private String githubLink;

    @Column(name = "linkedin_link")
    private String linkedInLink;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    public ResumeDetail() {

    }

    public ResumeDetail(String summary, String githubLink, String linkedInLink, Resume resume) {
        this.summary = summary;
        this.githubLink = githubLink;
        this.linkedInLink = linkedInLink;
        this.resume = resume;
    }

    public Long getId() {
        return this.id;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getGithubLink() {
        return this.githubLink;
    }

    public String getLinkedInLink() {
        return this.linkedInLink;
    }

    public Resume getResume() {
        return this.resume;
    }
}
