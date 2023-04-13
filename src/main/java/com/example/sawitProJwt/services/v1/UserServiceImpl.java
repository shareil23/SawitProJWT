package com.example.sawitProJwt.services.v1;

import com.example.sawitProJwt.dto.request.UserLoginRequest;
import com.example.sawitProJwt.dto.request.UserNameUpdateRequest;
import com.example.sawitProJwt.dto.request.UserRegistrationRequest;
import com.example.sawitProJwt.dto.response.UserNameOnlyResponse;
import com.example.sawitProJwt.dto.response.UserResponse;
import com.example.sawitProJwt.entities.User;
import com.example.sawitProJwt.exception.CredentialsInvalidException;
import com.example.sawitProJwt.exception.ResourceDuplicateException;
import com.example.sawitProJwt.exception.ResourceNotFoundException;
import com.example.sawitProJwt.repositories.database.UserRepository;
import com.example.sawitProJwt.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse userRegistration(UserRegistrationRequest request) throws ResourceDuplicateException {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResourceDuplicateException("Data already exists");
        }

        User user = userRepository.save(User.builder()
                .phoneNumber(request.getPhoneNumber())
                .name(request.getName())
                .password(PasswordUtils.hashPassword(request.getPassword()))
                .build());

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .build();
    }

    @Override
    public User userLogin(UserLoginRequest request) throws CredentialsInvalidException {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new CredentialsInvalidException("Phone number or password is incorrect"));

        if (PasswordUtils.checkPassword(request.getPassword(), user.getPassword()))
        {
            return user;
        }

        throw new CredentialsInvalidException("Phone number or password is incorrect");
    }

    @Override
    public UserNameOnlyResponse userGetName(String phoneNumber) throws ResourceNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserNameOnlyResponse.builder()
                .name(user.getName())
                .build();
    }

    @Override
    public UserResponse userUpdateName(UserNameUpdateRequest request, String phoneNumber) throws ResourceNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Phone number or password is incorrect"));

        user.setName(request.getName());
        user.setUpdatedDate(new Date());
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .build();
    }
}
