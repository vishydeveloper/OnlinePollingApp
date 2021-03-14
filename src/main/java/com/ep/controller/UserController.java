package com.ep.controller;

import com.ep.eventprocessor.PollAddedPublisher;
import com.ep.eventprocessor.UserAddedPublisher;
import com.ep.model.User;
import com.ep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    private UserAddedPublisher userAddedPublisher;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/postUsers")
    public String postUsers(@RequestBody List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userAddedPublisher.publishUser(user);
            userRepository.save(user);
        }
        return "Valid Users configured !!";
    }

    @GetMapping("/users")
    public List<User> list() {
        return userRepository.findAll().stream().collect(toList());
    }

}
