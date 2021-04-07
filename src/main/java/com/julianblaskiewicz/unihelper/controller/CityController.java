package com.julianblaskiewicz.unihelper.controller;

import com.julianblaskiewicz.unihelper.entity.City;
import com.julianblaskiewicz.unihelper.exception.ResourceNotFoundException;
import com.julianblaskiewicz.unihelper.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {

    CityRepository repository;

    CityController(@Autowired CityRepository cityRepository) {
        this.repository = cityRepository;
    }

    @GetMapping(value = "api/city/{id}", produces = "application/json")
    @ResponseBody
    public City getCityById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id.toString(), "city"));
    }

    @DeleteMapping("api/city/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
