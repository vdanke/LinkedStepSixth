package org.step.linked.step.configuration.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.step.linked.step.configuration.events.custom.AppCustomEvent;

@Component
@Slf4j
public class AllEvents {

    @EventListener(classes = {ApplicationStartedEvent.class})
    public void applicationStartedEventListener(ApplicationStartedEvent event) {
        log.info("Application started event by annotation");
    }

    @Async
    @EventListener(classes = {AppCustomEvent.class})
    public void applicationCustomEvent(AppCustomEvent event) {
        log.info("Our custom event");
    }
}
