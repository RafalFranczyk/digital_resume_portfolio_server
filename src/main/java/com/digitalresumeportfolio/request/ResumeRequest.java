package com.digitalresumeportfolio.request;

import java.io.Serializable;

public class ResumeRequest implements Serializable {

    private String name;

    private String description;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
