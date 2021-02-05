package org.step.linked.step;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.step.linked.step.configuration.AppConfigProperties;
import org.step.linked.step.configuration.events.LogApplicationContextReadyEvent;
import org.step.linked.step.configuration.events.LogApplicationEnvironmentEvent;
import org.step.linked.step.configuration.events.LogApplicationStartingEvent;

@SpringBootApplication
//@EnableConfigurationProperties(AppConfigProperties.class)
@ConfigurationPropertiesScan(basePackages = {"org.step.linked.step.configuration"})
@EnableAspectJAutoProxy
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.addListeners(new LogApplicationEnvironmentEvent(), new LogApplicationStartingEvent(), new LogApplicationContextReadyEvent());
		app.run(args);
	}

}
