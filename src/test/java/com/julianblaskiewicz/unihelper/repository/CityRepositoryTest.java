package com.julianblaskiewicz.unihelper.repository;

import com.julianblaskiewicz.unihelper.entity.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CityRepositoryTest extends RepositoryTest {

    @Autowired
    CityRepository cityRepository; // under test

    private final static String DELETE_ALL_CITY_SQL = "DELETE FROM CITY";

    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS CITY (\n" +
            "    ID LONG NOT NULL,\n" +
            "    CITY_NAME VARCHAR(255) NOT NULL,\n" +
            "    LONGITUDE DECIMAL(9,6) NOT NULL,\n" +
            "    LATITUDE DECIMAL(8,6) NOT NULL,\n" +
            "    ADMIN_NAME VARCHAR(255) NOT NULL,\n" +
            "    POPULATION INT NOT NULL\n" +
            ");";

    private final static String INSERT_CITY_SQL = "INSERT INTO CITY (ID, CITY_NAME, LONGITUDE, LATITUDE, ADMIN_NAME, POPULATION) VALUES (1,'London','51.5072','-0.1275','City of London',10979000)";

    @BeforeEach
    void beforeAll() {
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(super.getDataSource());
        jdbcTemplate.update(CREATE_TABLE_SQL);
        jdbcTemplate.update(DELETE_ALL_CITY_SQL);
        jdbcTemplate.update(INSERT_CITY_SQL);
    }

    @Test
    void shouldFindCityById() {
        Optional<City> city = cityRepository.findById((long) 1);
        assertTrue(city.isPresent());
        assertEquals(city.get().getCityName(), "London");
    }

    @Test
    void shouldFindCityByName() {
        Optional<City> city = cityRepository.findByCityName("London");
        assertTrue(city.isPresent());
        assertEquals(city.get().getCityName(), "London");
    }

    @Test
    void shouldNotFindCityIfNotPresent() {
        Optional<City> city = cityRepository.findByCityName("Chicago");
        assertFalse(city.isPresent());
    }

}