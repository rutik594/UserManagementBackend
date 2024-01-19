package com.example.usermanagement.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
@Entity
public class User {
    public User() {
    }

    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;

    public long getRoleType() {
        return roleType;
    }

    public void setRoleType(long roleType) {
        this.roleType = roleType;
    }

    private long roleType=1L;
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    public User(String userName, String userFirstName, String userLastName, String userPassword, Role role,Long roleType) {
        this.userName = userName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.role = role;
        this.roleType=roleType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
