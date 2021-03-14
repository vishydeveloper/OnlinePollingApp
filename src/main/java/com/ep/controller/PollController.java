package com.ep.controller;

import com.ep.eventprocessor.PollAddedPublisher;
import com.ep.model.Poll;
import com.ep.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping
public class PollController {

    private final PollRepository pollRepository;

    @Autowired
    private PollAddedPublisher pollAddedPublisher;

    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
//        List<Poll> polls = pollRepository.findAll().stream().collect(toList());
//        updatePolls(polls);
    }

    private void updatePolls(List<Poll> polls) {
        for (int i = 0; i < polls.size(); i++) {
            Poll poll = polls.get(i);
            pollAddedPublisher.publishUser(poll);
        }
    }

    @PostMapping("/postPolls")
    public String postPolls(@RequestBody List<Poll> polls) {
        for (int i = 0; i < polls.size(); i++) {
            Poll poll = polls.get(i);
            pollAddedPublisher.publishUser(poll);
            pollRepository.save(poll);
        }
        return "Valid Polls configured !!";
    }

    @GetMapping("/polls")
    public List<Poll> list() {
        return pollRepository.findAll().stream().collect(toList());
    }
}
