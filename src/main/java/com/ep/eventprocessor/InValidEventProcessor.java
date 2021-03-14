package com.ep.eventprocessor;

import com.ep.event.InvalidPollingEvent;
import com.ep.model.ErrorMessage;
import com.ep.model.Event;
import com.ep.model.ValidationError;
import com.ep.repository.ErrorMessageRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InValidEventProcessor {

    private final ErrorMessageRepository errorMessageRepository;

    public InValidEventProcessor(ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }

    @EventListener
    @Async
    public void handleEvent(InvalidPollingEvent<Event, ValidationError> invalidPollingEvent) {
        ValidationError error = invalidPollingEvent.getError();
        Event event = invalidPollingEvent.getData();
        System.out.println("Invalid Event " + event + " :: Error :: " + error.name());
        ErrorMessage errorMessage = new ErrorMessage(event.getUseridentity(), event.getPollingname(), event.getUseroption(), error.name());
        errorMessageRepository.save(errorMessage);
    }
}
