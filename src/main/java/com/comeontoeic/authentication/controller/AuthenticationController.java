package com.comeontoeic.authentication.controller;

import com.comeontoeic.authentication.dto.request.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(signUpRequest, HttpStatus.OK);
    }
}
