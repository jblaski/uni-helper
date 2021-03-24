package com.julianblaskiewicz.unihelper.controller;

import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import com.julianblaskiewicz.unihelper.repository.LearningProviderRepository;
import com.julianblaskiewicz.unihelper.service.LearningProviderServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class LearningProviderController {

    LearningProviderRepository repository;

    LearningProviderServiceImplementation service;

    LearningProviderController(@Autowired LearningProviderRepository learningProviderRepository,
                               @Autowired LearningProviderServiceImplementation learningProviderServiceImplementation) {
        this.repository = learningProviderRepository;
        this.service = learningProviderServiceImplementation;
    }

    // e.g. http://localhost:8080/api/learningprovider?id=10007768
    @GetMapping(value = "api/learningprovider", produces = "application/json")
    @ResponseBody
    public LearningProvider getLearningProviderById(@RequestParam long id) {
        Optional<LearningProvider> learningProvider = repository.findById(id);
        if(!learningProvider.isPresent()) throw new ResponseStatusException( //TODO Custom error controller, to show message
                HttpStatus.NOT_FOUND, "Learning Provider couldn't be found!"
        );
        return learningProvider.get();
    }

    //TODO move to own controller? Not sure
    @GetMapping(value = "api/closestlearningprovider", produces = "application/json")
    @ResponseBody
    public List<LearningProvider> getClosest(@RequestParam String cityName) {
        return service.findLearningProviderByProximityToCity(cityName, 3);
    }

    @DeleteMapping("api/learningprovider/{id}")
    void deleteEmployee(@PathVariable long id) {
        repository.deleteById(id);
    }
}
