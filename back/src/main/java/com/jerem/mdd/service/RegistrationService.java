package com.jerem.mdd.service;

import com.jerem.mdd.dto.RegisterRequestDto;
import com.jerem.mdd.dto.UserSummaryDto;


/**
 * Service interface for handling user registration.
 * <p>
 * This interface defines the method for registering a new user with the provided registration data.
 * </p>
 */
public interface RegistrationService {
    public UserSummaryDto register(RegisterRequestDto registerRequestDto);
}
