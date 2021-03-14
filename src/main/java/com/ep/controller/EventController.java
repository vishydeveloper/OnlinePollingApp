package com.ep.controller;

import com.ep.eventprocessor.PollingEventPublisher;
import com.ep.model.Event;
import com.ep.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Autowired
    private PollingEventPublisher pollingEventPublisher;

    @PostMapping("/postEvents")
    public String postEvents(@RequestBody List<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            pollingEventPublisher.publishEvent(event);
            eventRepository.save(event);
        }
        return "Poll event sent !!";
    }

    @GetMapping("/events")
    public List<Event> list() {
        return eventRepository.findAll().stream().collect(toList());
    }
}
