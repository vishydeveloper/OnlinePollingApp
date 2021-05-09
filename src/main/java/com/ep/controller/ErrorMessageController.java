package com.ep.controller;

import com.ep.model.ErrorMessage;
import com.ep.model.ErrorMessageAggregation;
import com.ep.repository.ErrorMessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping
public class ErrorMessageController {

    private final ErrorMessageRepository errorMessageRepository;

    public ErrorMessageController(ErrorMessageRepository errorMessageRepository) {

        this.errorMessageRepository = errorMessageRepository;
    }

    @GetMapping("/errors")
    public List<ErrorMessageAggregation> errorMessageAggregation() {

        List<ErrorMessage> errorMessages = errorMessageRepository.findAll().stream().collect(toList());
        Map<String, Integer> errorMessageMap = new HashMap<>();
//        How to convert this to Java8 Stream syntax
//        errorMessages.stream().forEach(errorMessage -> {
//                    if (errorMessageMap.containsKey(errorMessage.getErrormessage())) {
//                        errorMessageMap.put(errorMessage.getErrormessage(), errorMessageMap.get(errorMessage.getErrormessage() + 1));
//                    } else {
//                        errorMessageMap.put(errorMessage.getErrormessage(), 1);
//                    }
//                }
//        );
        for (ErrorMessage errorMessage : errorMessages) {
            String key = errorMessage.getErrormessage();
            if (errorMessageMap.containsKey(key)) {
                Integer integer = errorMessageMap.get(key);
                integer += 1;
                errorMessageMap.put(key, integer);
            } else {
                errorMessageMap.put(key, 1);
            }
        }

        List<ErrorMessageAggregation> errorMessageAggregations = new ArrayList<>();
        Iterator<String> resultsIterator = errorMessageMap.keySet().iterator();
        while (resultsIterator.hasNext()) {
            String key = resultsIterator.next();
            Integer count = errorMessageMap.get(key);
            ErrorMessageAggregation errorMessageAggregation = new ErrorMessageAggregation(key, count);
            errorMessageAggregations.add(errorMessageAggregation);
        }
        return errorMessageAggregations;
    }

}
