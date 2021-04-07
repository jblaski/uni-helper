package com.julianblaskiewicz.unihelper.controller;

import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import com.julianblaskiewicz.unihelper.service.LearningProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ProximityController {

    LearningProviderService service;

    ProximityController(@Autowired LearningProviderService learningProviderService) {
        this.service = learningProviderService;
    }

    //TODO input sanitization, what to return for bad inputs, should this or the service handle an invalid town
    @GetMapping(value = "api/proximity", produces = "application/json")
    @ResponseBody
    public List<LearningProvider> getClosest(@RequestParam Map<String, String> parameters) throws Exception {
        if (parameters.containsKey("cityName") && parameters.containsKey("count")) {
            int count = Integer.parseInt(parameters.get("count"));
            return service.findLearningProvidersByProximityToCity(parameters.get("cityName"), count);
        } else throw new Exception("Incorrect parameters");
    }
}
