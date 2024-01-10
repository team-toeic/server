package com.comeontoeic.authentication.controller;

import com.comeontoeic.authentication.dto.request.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/*")
public class AuthenticationController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(signUpRequest, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(signUpRequest, HttpStatus.OK);
    }
}
