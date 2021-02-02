package org.step.linked.step;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.step.linked.step.configuration.AppConfigProperties;

@SpringBootApplication
//@EnableConfigurationProperties(AppConfigProperties.class)
@ConfigurationPropertiesScan(basePackages = {"org.step.linked.step.configuration"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
