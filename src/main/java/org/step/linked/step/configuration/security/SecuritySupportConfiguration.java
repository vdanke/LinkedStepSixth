package org.step.linked.step.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecuritySupportConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        final int strength = 8;
        return new BCryptPasswordEncoder(strength);
    }
}
