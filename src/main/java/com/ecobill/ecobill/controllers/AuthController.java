package com.ecobill.ecobill.controllers;

import com.ecobill.ecobill.domain.dto.loginRequestDto;
import com.ecobill.ecobill.domain.dto.signUpRequestDto;
import com.ecobill.ecobill.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody signUpRequestDto signUpRequest) {
        customerService.registerUser(signUpRequest);
        return ResponseEntity.ok().body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody loginRequestDto loginRequest) {
        String jwt = customerService.authenticateUser(loginRequest);
        return ResponseEntity.ok().body("Bearer " + jwt);
    }
}
