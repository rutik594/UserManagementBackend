package com.example.usermanagement.service;

import com.example.usermanagement.dao.RoleDao;
import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.entity.Role;
import com.example.usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

  /*  public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);


//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
}*/

    public ResponseEntity<?> registerNewUser(User user) {
        try {
            // Check if username already exists
            if (userDao.existsByUserName(user.getUserName())) {
                // If it does, send an HTTP error response without a body
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Encode the user's password before saving
            String encodedPassword = getEncodedPassword(user.getUserPassword());
            user.setUserPassword(encodedPassword);
            Role role = roleDao.findById(user.getRoleType())
                    .orElseThrow(() -> new RoleNotFoundException("Role not found"));
            user.setRole(role);
            // Save the user to the database
            userDao.save(user);

            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();

            // Return a 500 Internal Server Error without a body
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public List<User> getAllUsers() {
        return (List<User>) userDao.findAll();
    }

    public Optional<User> updateUser(String userName, Map<String, Object> updates) {
        return userDao.findById(userName)
                .map(existingUser -> {
                    existingUser.setUserFirstName((String) updates.getOrDefault("userFirstName", existingUser.getUserFirstName()));
                    existingUser.setUserLastName((String) updates.getOrDefault("userLastName", existingUser.getUserLastName()));
                    existingUser.setUserPassword((String) updates.getOrDefault("userPassword", existingUser.getUserPassword()));
                    return userDao.save(existingUser);
                });
    }



    @Transactional
    public void deleteUserByUserName(String userName) {
        // Add additional validation if needed
        userDao.deleteByUserName(userName);
    }

    public User getUser(String username) {
        return userDao.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}