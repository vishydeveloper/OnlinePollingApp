package com.ep.eventprocessor;

import com.ep.event.PollAddedEvent;
import com.ep.model.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PollAddedPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUser(Poll poll) {
        applicationEventPublisher.publishEvent(new PollAddedEvent(poll));
    }
}
