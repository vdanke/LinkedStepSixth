package org.step.linked.step.configuration.events.custom;

import org.springframework.context.ApplicationEvent;

public class AppCustomEvent extends ApplicationEvent {

    private String name;

    public AppCustomEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
