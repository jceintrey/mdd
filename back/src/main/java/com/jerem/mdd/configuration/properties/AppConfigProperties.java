package com.jerem.mdd.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "mdd")
public class AppConfigProperties {
    @Pattern(regexp = "^[A-Za-z0-9@$!%*?&=]{32,}$", message = "Key size must be 256 bits at least")
    @NotNull
    @NotEmpty
    private String jwtsecretkey;
    private String jwtexpirationtime;
}
