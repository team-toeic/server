package com.comeontoeic.authentication.controller;

import com.comeontoeic.authentication.dto.MemberDto;
import com.comeontoeic.authentication.dto.request.LoginRequest;
import com.comeontoeic.authentication.dto.request.SignupRequest;
import com.comeontoeic.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authenticationService.login(loginRequest);
        return new ResponseEntity<>("Login Success", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        System.out.println(signupRequest.getTermsOfServices());
        authenticationService.signup(signupRequest);
        return new ResponseEntity<>("Signup Success", HttpStatus.OK);
    }
}
