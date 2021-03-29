package com.julianblaskiewicz.unihelper.controller;

import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import com.julianblaskiewicz.unihelper.repository.LearningProviderRepository;
import com.julianblaskiewicz.unihelper.service.LearningProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class LearningProviderController {

    LearningProviderRepository repository;

    LearningProviderController(@Autowired LearningProviderRepository learningProviderRepository) {
        this.repository = learningProviderRepository;
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

    @DeleteMapping("api/learningprovider/{id}")
    void deleteLearningProvider(@PathVariable long id) {
        repository.deleteById(id);
    }
}
