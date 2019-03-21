package com.digitalresumeportfolio.request;

import java.io.Serializable;
import java.sql.Date;

public class ResumeWorkExperienceRequest implements Serializable {

    private Date startDate;

    private Date endDate;

    private String companyName;

    private String workTitle;

    private String workDescription;

    private Long resumeId;

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

    public Long getResumeId() {
        return this.resumeId;
    }
}
