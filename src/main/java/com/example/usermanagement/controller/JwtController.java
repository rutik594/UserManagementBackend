package com.example.usermanagement.controller;

import com.example.usermanagement.entity.JwtRequest;
import com.example.usermanagement.entity.JwtResponse;
import com.example.usermanagement.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}
