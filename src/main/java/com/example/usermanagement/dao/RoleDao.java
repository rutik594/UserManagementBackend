package com.example.usermanagement.dao;

import com.example.usermanagement.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
