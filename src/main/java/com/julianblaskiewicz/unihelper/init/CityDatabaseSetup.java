package com.julianblaskiewicz.unihelper.init;


import com.julianblaskiewicz.unihelper.entity.City;
import com.julianblaskiewicz.unihelper.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CityDatabaseSetup {

    @Value("${spring.datasource.driverClassName}")
    String driverClassName;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    CityRepository repository;

    CityDatabaseSetup(@Autowired CityRepository repository) {
        this.repository = repository;
    }

    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS CITY (\n" +
            "    ID LONG NOT NULL,\n" +
            "    CITY_NAME VARCHAR(255) NOT NULL,\n" +
            "    LONGITUDE DECIMAL(9,6) NOT NULL,\n" +
            "    LATITUDE DECIMAL(8,6) NOT NULL,\n" +
            "    ADMIN_NAME VARCHAR(255) NOT NULL,\n" +
            "    POPULATION INT NOT NULL\n" +
            ");";

    /**
     * Takes one line of CSV input and returns an array of Strings representing a row of data
     * @param line one line of the csv input
     * @return array of Strings, one for each field
     */
    public String[] parseLine(String line) {
        String[] cols = line.split(",");
        //cols[0] = cols[0].substring(1);
        //cols[cols.length - 1] = cols[cols.length - 1].substring(0, cols[cols.length - 1].length() - 1);
        return cols;
    }

    /**
     * Creates a datasource from application.properties
     * //TODO create datasource based on profile
     * @return the datasource to perform setup on
     */
    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Reads CSV from passed filepath, converts to entities, saves to passed com.julianblaskiewicz.unihelper.repository
     * @param filepath path to CSV containing data
     * @throws IOException if file isn't found
     */
    public void setupDatabaseEntities(String filepath) throws IOException {
        log.info("Started loading [" + filepath + "]...");
        String result = null;
        DataInputStream reader = new DataInputStream(new FileInputStream(filepath));
        int nBytesToRead = reader.available();
        if (nBytesToRead > 0) {
            byte[] bytes = new byte[nBytesToRead];
            reader.read(bytes);
            result = new String(bytes);
            String[] lines = result.split("\r\n");
            List<String[]> cells = Arrays.stream(lines)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
            List<String[]> data = cells.subList(1, cells.size());

            for (String[] entry : data) {
                repository.save(City.builder()
                        .cityName(entry[0])
                        .latitude(Double.parseDouble(entry[1]))
                        .longitude(Double.parseDouble(entry[2]))
                        .adminName(entry[5].replace("\"", ""))
                        .population(Integer.parseInt(entry[7]))
                        .build());
            }
            log.info("...finished loading database");
        }
    }
}