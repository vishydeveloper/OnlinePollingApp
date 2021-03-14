package com.ep.controller;

import com.ep.eventprocessor.UserAddedPublisher;
import com.ep.model.Result;
import com.ep.model.ResultAggregation;
import com.ep.model.User;
import com.ep.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping
public class ResultController {

    private final ResultRepository resultRepository;

    public ResultController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping("/results")
    public List<Result> list() {
        return resultRepository.findAll().stream().collect(toList());
    }

    @GetMapping("/resultaggregation")
    public List<ResultAggregation> resultAggregation() {

        List<Result> results = resultRepository.findAll().stream().collect(toList());
        Map<String, Integer> resultMap = new HashMap<>();
        for(Result result : results){
            String key = result.getPollingname() + ":" + result.getUseroption();
            if(resultMap.containsKey(key)){
                Integer integer = resultMap.get(key);
                integer += 1;
                resultMap.put(key, integer);
            }else{
                resultMap.put(key, 1);
            }
        }

        List<ResultAggregation> resultAggregationList = new ArrayList<>();
        Iterator<String> resultsIterator = resultMap.keySet().iterator();
        while(resultsIterator.hasNext()){
            String key = resultsIterator.next();
            Integer count = resultMap.get(key);
            String[] split = key.split(":");
            ResultAggregation resultAggregation = new ResultAggregation(split[0], split[1], count);
            resultAggregationList.add(resultAggregation);
        }
        return resultAggregationList;
    }

}
