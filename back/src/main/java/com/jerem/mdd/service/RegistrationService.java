package com.jerem.mdd.service;

import com.jerem.mdd.dto.RegisterRequestDto;
import com.jerem.mdd.dto.UserSummaryDto;

public interface RegistrationService {
    public UserSummaryDto register(RegisterRequestDto registerRequestDto);
}
