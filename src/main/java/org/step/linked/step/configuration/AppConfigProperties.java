package org.step.linked.step.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfigProperties {

    private Token token = new Token();

    @Getter
    @Setter
    public static class Token {
        @Size(min = 140, max = 512)
        private String secret;
        @Min(value = 2_000_000)
        @Max(value = 10_000_000)
        private Long expiration;
        private Map<String, String> userRoles = new HashMap<>();
    }
}
