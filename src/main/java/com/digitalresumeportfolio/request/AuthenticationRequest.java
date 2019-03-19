package com.digitalresumeportfolio.request;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return this.usernameOrEmail;
    }

    public String getPassword() {
        return this.password;
    }
}

