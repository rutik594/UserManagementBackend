package com.example.usermanagement.controller;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import  com.example.usermanagement.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserDao userDao;

  /*  @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }*/

    @PostMapping({"/registerNewUser"})
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerNewUser(user));
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    public List<User> forUser(){
        return userService.getAllUsers();
    }

    @GetMapping({"/forUser/{username}"})
    public User getUser(@PathVariable  String username){
        return userService.getUser(username);
    }

    @PatchMapping ({"/forAdmin/edit/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<User> forUser(@PathVariable String userName, @RequestBody Map<String, Object> user){
        Optional<User> updatedUserData = userService.updateUser(userName, user);
        return updatedUserData.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/delete/{userName}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteUserByUserName(@PathVariable String userName) {
        userService.deleteUserByUserName(userName);
        return ResponseEntity.ok().build();
    }

}
