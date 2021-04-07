package com.julianblaskiewicz.unihelper.controller;

import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import com.julianblaskiewicz.unihelper.exception.ResourceNotFoundException;
import com.julianblaskiewicz.unihelper.repository.LearningProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LearningProviderController {

    LearningProviderRepository repository;

    LearningProviderController(@Autowired LearningProviderRepository learningProviderRepository) {
        this.repository = learningProviderRepository;
    }

    // e.g. http://localhost:8080/api/learningprovider/10007768
    @GetMapping(value = "api/learningprovider/{ukprn}", produces = "application/json")
    @ResponseBody
    public LearningProvider getLearningProviderById(@PathVariable Long ukprn) {
        return repository.findById(ukprn).orElseThrow(() -> new ResourceNotFoundException("ukprn", ukprn.toString(), "learning provider"));
    }

    @DeleteMapping("api/learningprovider/{ukprn}")
    void deleteLearningProvider(@PathVariable long ukprn) {
        repository.deleteById(ukprn);
    }
}
