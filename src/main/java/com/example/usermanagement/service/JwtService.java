package com.example.usermanagement.service;

import com.example.usermanagement.dao.UserDao;
import com.example.usermanagement.entity.JwtRequest;
import com.example.usermanagement.entity.JwtResponse;
import com.example.usermanagement.entity.Role;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        // Check if username exists
        if (!userDao.existsById(userName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username does not exist");
        }

        try {
            // Authenticate only if username exists
            authenticate(userName, userPassword);

            UserDetails userDetails = loadUserByUsername(userName);
            String newGeneratedToken = jwtUtil.generateToken(userDetails);

            User user = userDao.findById(userName).get();
            JwtResponse jwtResponse = new JwtResponse(user, newGeneratedToken);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();

            // Return a 400 Bad Request without a body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).orElse(null);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Assuming user has only one role
        Role role = user.getRole();

        // Make sure role is not null (handle null check if needed)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
