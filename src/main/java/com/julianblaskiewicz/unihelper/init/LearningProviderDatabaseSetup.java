package com.julianblaskiewicz.unihelper.init;


import com.julianblaskiewicz.unihelper.repository.LearningProviderRepository;
import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//TODO create parent class for DatabaseSetup, to extend stuff like this from
@Slf4j
@Component
public class LearningProviderDatabaseSetup {

    @Value("${spring.datasource.driverClassName}")
    String driverClassName;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    LearningProviderRepository repository;

    LearningProviderDatabaseSetup(@Autowired LearningProviderRepository repository) {
        this.repository = repository;
    }

    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS LEARNING_PROVIDER (\n" +
            "    UKPRN LONG NOT NULL,\n" +
            "    PROVIDER_NAME VARCHAR(255) NOT NULL,\n" +
            "    VIEW_NAME VARCHAR(255),\n" +
            "    SORT_NAME VARCHAR(255),\n" +
            "    ALIAS VARCHAR(255),\n" +
            "    FLAT_NAME VARCHAR(255),\n" +
            "    BUILDING VARCHAR(255),\n" +
            "    LOCALITY VARCHAR(255),\n" +
            "    STREET_NAME VARCHAR(255),\n" +
            "    TOWN VARCHAR(255),\n" +
            "    POSTCODE VARCHAR(255),\n" +
            "    WEBSITE VARCHAR(255),\n" +
            "    WIKIPEDIA VARCHAR(255),\n" +
            "    GROUPS VARCHAR(255),\n" +
            "    LONGITUDE DECIMAL(9,6),\n" +
            "    LATITUDE DECIMAL(8,6),\n" +
            "    EASTING INT,\n" +
            "    NORTHING INT,\n" +
            "    GTR_ID VARCHAR(255),\n" +
            "    HESA_ID INT\n" +
            ");";

    /**
     * Takes one line of CSV input and returns an array of Strings representing a row of data
     * @param line one line of the csv input
     * @return array of Strings, one for each field
     */
    public String[] parseLine(String line) {
        String[] cols = line.split("\",\"");
        cols[0] = cols[0].substring(1);
        cols[cols.length - 1] = cols[cols.length - 1].substring(0, cols[cols.length - 1].length() - 1);
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
            String[] lines = result.split("\n");
            List<String[]> cells = Arrays.stream(lines)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
            List<String[]> data = cells.subList(1, cells.size());


            for (String entry[] : data) {
                //log.info("Creating entity for " + entry[1]);
                String eastingString = entry[16];
                String northingString = entry[17];
                Double longitude = null;
                try {
                    longitude = Double.valueOf(entry[14]);
                } catch (NumberFormatException e) {
                    log.error("Error parsing double: {}", e.getMessage());
                }
                Double latitude = null;
                try {
                    latitude = Double.valueOf(entry[15]);
                } catch (NumberFormatException e) {
                    log.error("Error parsing double: {}", e.getMessage());
                }
                repository.save(LearningProvider.builder()
                        .UKPRN(Integer.parseInt(entry[0]))
                        .providerName(entry[1])
                        .viewName(entry[2])
                        .sortName(entry[3])
                        .alias(entry[4])
                        .flatNameNumber(entry[5])
                        .buildingNameNumber(entry[6])
                        .locality(entry[7])
                        .streetName(entry[8])
                        .town(entry[9])
                        .postcode(entry[10])
                        .websiteURL(entry[11])
                        .wikipediaURL(entry[12])
                        .groups(entry[13])
                        .longitude(longitude)
                        .latitude(latitude)
                        .GTRid(entry[18])
                        .HESAid(entry[19])
                        .build());
            }
            log.info("...finished loading database");
        }
    }
}