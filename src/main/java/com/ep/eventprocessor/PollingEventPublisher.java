package com.ep.eventprocessor;

import com.ep.event.PollingEvent;
import com.ep.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PollingEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Event event) {
        applicationEventPublisher.publishEvent(new PollingEvent(event));
    }
}
