package com.julianblaskiewicz.unihelper.repository;
import com.julianblaskiewicz.unihelper.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    //TODO probably not very secure, expect some injection nonsense is possible
    Optional<City> findByCityName(String cityName);
}