package com.shubham.controller;

import com.shubham.dto.AuthRequest;
import com.shubham.dto.AuthResponse;
import com.shubham.dto.Role;
import com.shubham.dto.User;
import com.shubham.exception.UserException;
import com.shubham.repository.UserRepository;
import com.shubham.security.JwtAuthHelper;
import com.shubham.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    JwtAuthHelper authHelper;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) throws UserException {
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new UserException("An User with username already exists: " + user.getUsername());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Authentication authentication = authenticate(request.getUsername(), request.getPassword(), userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = authHelper.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        AuthResponse authResponse = AuthResponse.builder()
                .authToken(token)
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password, UserDetails userDetails) {
        if(userDetails == null){
            throw new BadCredentialsException("Username or Password not valid!");
        }
        if(!encoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Username or Password not valid!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
