package com.julianblaskiewicz.unihelper.controller;

import com.julianblaskiewicz.unihelper.entity.City;
import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import com.julianblaskiewicz.unihelper.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class CityController {

    CityRepository repository;

    CityController(@Autowired CityRepository cityRepository) {
        this.repository = cityRepository;
    }

    @GetMapping(value = "api/city", produces = "application/json")
    @ResponseBody
    public City getCityById(@RequestParam long id) {
        Optional<City> city = repository.findById(id);
        if(!city.isPresent()) throw new ResponseStatusException( //TODO Custom error controller, to show message
                HttpStatus.NOT_FOUND, "City couldn't be found!"
        );
        return city.get();
    }

    @DeleteMapping("api/city/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmployee(@PathVariable long id) {
        repository.deleteById(id);
    }

}
