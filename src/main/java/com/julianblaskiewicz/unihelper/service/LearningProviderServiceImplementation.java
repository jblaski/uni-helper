package com.julianblaskiewicz.unihelper.service;

import com.julianblaskiewicz.unihelper.entity.City;
import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import com.julianblaskiewicz.unihelper.repository.CityRepository;
import com.julianblaskiewicz.unihelper.repository.LearningProviderRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LearningProviderServiceImplementation implements LearningProviderServiceInterface {

    private LearningProviderRepository learningProviderRepository;
    private CityRepository cityRepository;

    public LearningProviderServiceImplementation(@Autowired LearningProviderRepository learningProviderRepository,
                                                 @Autowired CityRepository cityRepository) {
        this.learningProviderRepository = learningProviderRepository;
        this.cityRepository = cityRepository;
    }

    /**
     *
     * @return list of
     */
    @Override
    public List<LearningProvider> findLearningProviderByProximityToCity(String cityName, int numberToReturn) {
        // Get the City entity corresponding to the name
        Optional<City> cityOptional = cityRepository.findByCityName(cityName);
        if (!cityOptional.isPresent()) throw new ResponseStatusException( //TODO Custom error controller, to show message
                HttpStatus.NOT_FOUND, "City couldn't be found!"
        );
        City city = cityOptional.get();

        // Get all the LearningProviders (expect his can be optimized, isn't very scalable)
        List<LearningProvider> learningProviders = learningProviderRepository.findAll();

        // Calculate difference in Latitude and Longitude for each learningProvider
        HashMap<LearningProvider, Double> learningProviderIdToDistance = new HashMap<>();

        for (LearningProvider learningProvider : learningProviders) {
            Double latDiff = Math.abs(learningProvider.getLatitude() - city.getLatitude());
            Double lonDiff = Math.abs(learningProvider.getLongitude() - city.getLongitude());
            Double distance = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2));
            learningProviderIdToDistance.put(learningProvider, distance);
        }

        // Sort by value
        LinkedHashMap<LearningProvider, Double> sortedHashMap = sortByValue(learningProviderIdToDistance);

        List<LearningProvider> closestLearningProviders = new ArrayList<>(sortedHashMap.keySet());

        return closestLearningProviders.subList(0, numberToReturn);
    }

    // function to sort hashmap by values
    public LinkedHashMap<LearningProvider, Double> sortByValue(HashMap<LearningProvider, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<LearningProvider, Double> > list =
                new LinkedList<Map.Entry<LearningProvider, Double> >(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        LinkedHashMap<LearningProvider, Double> temp = new LinkedHashMap<LearningProvider, Double>();
        for (Map.Entry<LearningProvider, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
