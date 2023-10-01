package com.example.movierama_api.controllers;

import com.example.movierama_api.dto.UserRequestDTO;
import com.example.movierama_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Long> loginUser(@RequestBody UserRequestDTO userRequestDTO) {

        try {
            Long existingUserId = userService.login(userRequestDTO);

            if (Objects.nonNull(existingUserId)) {
                return ResponseEntity.status(HttpStatus.OK).body(existingUserId);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(@RequestBody UserRequestDTO userRequestDTO) {

        Long registeredUserId = userService.register(userRequestDTO);

        if (Objects.nonNull(registeredUserId)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserId);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
