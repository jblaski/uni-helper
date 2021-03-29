package com.julianblaskiewicz.unihelper.service;

import com.julianblaskiewicz.unihelper.entity.LearningProvider;

import java.util.List;

public interface LearningProviderServiceInterface {

    public List<LearningProvider> findLearningProvidersByProximityToCity(String cityName, int numberToReturn);
}
