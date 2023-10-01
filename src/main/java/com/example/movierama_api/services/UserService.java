package com.example.movierama_api.services;

import com.example.movierama_api.models.User;
import com.example.movierama_api.dto.UserRequestDTO;
import com.example.movierama_api.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long login(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByUsername(userRequestDTO.getUsername());

        if (Objects.nonNull(user)) {
            boolean isPasswordMatchingTheUserName = passwordEncoder.matches(userRequestDTO.getPassword(), user.getPasswordHash());

            if (isPasswordMatchingTheUserName) {
                return user.getUserId();
            }
        }

        return null;
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public Long register(UserRequestDTO userRequestDTO) {
        boolean isUserNameTaken = Objects.nonNull(userRepository.findByUsername(userRequestDTO.getUsername()));

        if (isUserNameTaken) {
            return null;
        }

        String hashedPassword = passwordEncoder.encode(userRequestDTO.getPassword());

        User newUser = new User(userRequestDTO.getUsername(), userRequestDTO.getEmail(), hashedPassword);

        userRepository.save(newUser);

        return newUser.getUserId();
    }
}
