package com.ep.eventprocessor;

import com.ep.event.*;
import com.ep.model.Event;
import com.ep.model.Poll;
import com.ep.model.User;
import com.ep.model.ValidationError;
import com.ep.repository.PollRepository;
import com.ep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class EventProcessor {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    List<String> userList;
    Map<String, Poll> pollMap;
    private final UserRepository userRepository;
    private final PollRepository pollRepository;

    public EventProcessor(UserRepository userRepository, PollRepository pollRepository) {
        userList = new ArrayList<>();
        pollMap = new HashMap<>();
        this.userRepository = userRepository;
        List<User> users = userRepository.findAll().stream().collect(toList());
        updateUsers(users);

        this.pollRepository = pollRepository;
        List<Poll> polls = pollRepository.findAll().stream().collect(toList());
        updatePolls(polls);
    }

    private void updatePolls(List<Poll> polls) {
        for (int i = 0; i < polls.size(); i++) {
            Poll poll = polls.get(i);
            pollMap.put(poll.getPollingname(), poll);
        }
    }

    private void updateUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userList.add(user.getUseridentity());
        }
    }


    @EventListener
    @Async
    public void handleEvent(PollingEvent<Event> pollingEvent) {
        Event event = pollingEvent.getData();
        System.out.println("Event Processor " + event);
        ValidationError validationError = validateEvent(event);
        if (validationError != null) {
            publishInvalidEvent(event, validationError);
        } else {
            publishPollResultEvent(event);
        }
    }

    @EventListener
    @Async
    public void handleEvent(UserAddedEvent<User> userAddedEvent) {
        User user = userAddedEvent.getData();
        userList.add(user.getUseridentity());
    }

    @EventListener
    @Async
    public void handlePoll(PollAddedEvent<Poll> pollAddedEvent) {
        Poll poll = pollAddedEvent.getData();
        pollMap.put(poll.getPollingname(), poll);
    }

    private ValidationError validateEvent(Event event) {
        if (!userList.contains(event.getUseridentity())) {
            return ValidationError.InvalidUser;
        }else if (!pollMap.keySet().contains(event.getPollingname())) {
            return ValidationError.InvalidPoll;
        }else if (!pollMap.get(event.getPollingname()).getValidoptions().contains(event.getUseroption())) {
            return ValidationError.InvalidOption;
        }else if (!validateStartAndEndTime(event)) {
            return ValidationError.InvalidTime;
        }


        return null;
    }

    private boolean validateStartAndEndTime(Event event){
        Poll poll = pollMap.get(event.getPollingname());
        Date eventDate = getDate(event.getEventtime());
        Date pollStartDate = getDate(poll.getStarttime());
        Date pollEndDate = getDate(poll.getEndtime());

        if(eventDate.after(pollStartDate) && eventDate.before(pollEndDate)){
            return true;
        }

        return false;
    }

    private Date getDate(String eventtime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(eventtime);
        } catch (ParseException e) {
            return null;
        }
    }

    public void publishInvalidEvent(Event event, ValidationError validationError) {
        applicationEventPublisher.publishEvent(new InvalidPollingEvent(event, validationError));
    }

    public void publishPollResultEvent(Event event) {
        applicationEventPublisher.publishEvent(new PollResultEvent(event));
    }
}
