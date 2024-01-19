package com.example.usermanagement;
import com.example.usermanagement.dao.RoleDao;
import com.example.usermanagement.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleDao roleRepository;

    @Override
    public void run(String... args) {
        initializeRoles();
    }

    private void initializeRoles() {
        createRoleIfNotExists(1L, "User", "Default role for regular users");
        createRoleIfNotExists(2L, "Admin", "Role for administrators");
    }

    private void createRoleIfNotExists(long roleId, String roleName, String roleDescription) {
        Role existingRole = roleRepository.findByRoleName(roleName);
        if (existingRole == null) {
            Role role = new Role(roleId, roleName, roleDescription, null);
            roleRepository.save(role);
        }
    }
}
