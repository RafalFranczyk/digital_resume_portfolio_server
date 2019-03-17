package com.digitalresumeportfolio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "roles")
    @NotEmpty
    private String roles;

    public UserRole() {

    }

    public UserRole(Long userId, String roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return this.roles;
    }
}
