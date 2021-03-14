package com.ep.eventprocessor;

import com.ep.event.PollResultEvent;
import com.ep.model.Event;
import com.ep.model.Result;
import com.ep.repository.ResultRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PollResultProcessor {


    private final ResultRepository resultRepository;

    public PollResultProcessor(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @EventListener
    @Async
    public void handleEvent(PollResultEvent<Event> pollResultEvent) {
        Event event = pollResultEvent.getData();
        System.out.println("PollResult Event " + event);
        Result result = new Result(event.getUseridentity(), event.getPollingname(), event.getUseroption());
        resultRepository.save(result);
    }
}
