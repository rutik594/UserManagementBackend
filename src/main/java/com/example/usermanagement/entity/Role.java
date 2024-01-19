package com.example.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Set;
@Entity
public class Role {


    public Role() {
    }

    public Role(long roleId, String roleName, String roleDescription, Set<User> users) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.users = users;
    }

    @Id
    private long roleId;
    private String roleName;
    private String roleDescription;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<User> users;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }




}
