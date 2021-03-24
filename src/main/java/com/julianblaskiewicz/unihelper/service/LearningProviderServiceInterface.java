package com.julianblaskiewicz.unihelper.service;

import com.julianblaskiewicz.unihelper.entity.LearningProvider;

import java.util.List;

public interface LearningProviderServiceInterface {

    public List<LearningProvider> findLearningProviderByProximityToCity(String cityName, int numberToReturn);
}
