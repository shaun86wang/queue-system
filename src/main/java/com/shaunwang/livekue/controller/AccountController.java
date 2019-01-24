package com.shaunwang.livekue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shaunwang.livekue.dto.ApiResponse;
import com.shaunwang.livekue.dto.LoginRequest;
import com.shaunwang.livekue.dto.SignUpRequest;
import com.shaunwang.livekue.model.Role;
import com.shaunwang.livekue.model.User;
import com.shaunwang.livekue.repository.UserRepository;
import com.shaunwang.livekue.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new ApiResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        String firstName = signUpRequest.getFirstName().trim();
        String lastName = signUpRequest.getLastName().trim();
        String studentName = firstName + " " + lastName;
        String displayName = firstName.substring(0, 1) + lastName.substring(0, 1) + signUpRequest.getStudentNumber().substring(6);
        User user = new User(studentName, displayName, signUpRequest.getStudentNumber(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.STUDENT);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
