package com.jerem.mdd.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.UserRepository;

import com.jerem.mdd.dto.RegisterRequestDto;
import com.jerem.mdd.dto.UserSummaryDto;
import com.jerem.mdd.exception.EmailAlreadyExistException;
import com.jerem.mdd.exception.UsernameAlreadyExistException;
import com.jerem.mdd.mapper.UserMapper;

@Service
public class DefaultRegistrationService implements RegistrationService {
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DefaultRegistrationService(UserManagementService userManagementService,
            UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userManagementService = userManagementService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

    }

    @Override
    public UserSummaryDto register(RegisterRequestDto registerRequestDto) {

        if (userManagementService.isEmailAlreadyUsed(registerRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("User email already used",
                    "RegistrationService.register");
        }
        if (userManagementService.isUsernameAlreadyUsed(registerRequestDto.getUsername())) {
            throw new UsernameAlreadyExistException("Username already used",
                    "RegistrationService.register");
        }

        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));


        User user = userMapper.toUserFromRegisterRequestDto(registerRequestDto);
        userRepository.save(user);
        return userMapper.toDto(user);

    }
}
