package com.ep.eventprocessor;

import com.ep.event.UserAddedEvent;
import com.ep.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserAddedPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUser(User user) {
        applicationEventPublisher.publishEvent(new UserAddedEvent(user));
    }
}
