package com.shubham.service;

import com.shubham.dto.User;
import com.shubham.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> deleteUser(long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }
}
