package com.jerem.mdd.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.UserDto;
import com.jerem.mdd.dto.UserProfileDto;
import com.jerem.mdd.model.User;


@Service
public class UserProfileService {


    private final UserManagementService userManagementService;
    private final AuthenticationService authenticationService;
    private final SubscriptionService subscriptionService;


    private UserProfileService(UserManagementService userManagementService,
            AuthenticationService authenticationService, SubscriptionService subscriptionService) {
        this.userManagementService = userManagementService;
        this.authenticationService = authenticationService;
        this.subscriptionService = subscriptionService;
    }


    public UserProfileDto getUserProfile() {

        String authenticatedUserEmail = authenticationService.getAuthenticatedUserEmail();

        Optional<User> authenticatedUser =
                userManagementService.getUserEntityByEmail(authenticatedUserEmail);

        if (authenticatedUser.isPresent()) {

            UserProfileDto userDto = new UserProfileDto();
            userDto.setEmail(authenticatedUser.get().getEmail());
            userDto.setUsername(authenticatedUser.get().getUsername());
            userDto.setSubscriptions(
                    subscriptionService.getSubscriptionsByUser(authenticatedUser.get().getId()));
            return userDto;

        } else
            throw new UsernameNotFoundException("No authenticated user found");

    }

}
