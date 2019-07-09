package com.example.demo.controller;

import com.example.demo.model.LoginForm;
import com.example.demo.model.SignUpForm;
import com.example.demo.security.services.AuthAndAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthAndAdminService authAndAdminService;

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws Exception {
        authAndAdminService.registerUser(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
        return new ResponseEntity<>(authAndAdminService.authenticateUser(loginForm), HttpStatus.OK);
    }
}
