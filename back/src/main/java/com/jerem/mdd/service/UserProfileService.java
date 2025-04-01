package com.jerem.mdd.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.UserDetailedDto;
import com.jerem.mdd.mapper.UserMapper;
import com.jerem.mdd.model.User;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserProfileService {


    private final UserManagementService userManagementService;
    private final AuthenticationService authenticationService;

    private final UserMapper userMapper;


    public UserProfileService(UserManagementService userManagementService,
            AuthenticationService authenticationService, UserMapper userMapper) {
        this.userManagementService = userManagementService;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }


    public UserDetailedDto getUserProfile() {

        String authenticatedUserEmail = authenticationService.getAuthenticatedUserEmail();

        Optional<User> authenticatedUser =
                userManagementService.getUserEntityByEmail(authenticatedUserEmail);

        if (authenticatedUser.isPresent()) {

            return userMapper.toUserProfileDto(authenticatedUser.get());

        } else
            throw new UsernameNotFoundException("No authenticated user found");

    }

}
