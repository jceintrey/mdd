package com.jerem.mdd.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.dto.UserProfileDto;
import com.jerem.mdd.mapper.UserMapper;
import com.jerem.mdd.model.Subscription;
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


    public UserProfileDto getUserProfile() {

        String authenticatedUserEmail = authenticationService.getAuthenticatedUserEmail();

        Optional<User> authenticatedUser =
                userManagementService.getUserEntityByEmail(authenticatedUserEmail);

        if (authenticatedUser.isPresent()) {

            return userMapper.toUserProfileDto(authenticatedUser.get());

        } else
            throw new UsernameNotFoundException("No authenticated user found");

    }

}
