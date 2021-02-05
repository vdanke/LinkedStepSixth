package org.step.linked.step.configuration.events.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Publisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    void publishEvent(final String name) {
        applicationEventPublisher.publishEvent(new AppCustomEvent(this, name));
    }
}
