package com.julianblaskiewicz.unihelper.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Provides an in-memory database to perform tests against, with a jdbcTemplate for executing setup SQL.
 * Using this means tests don't have to rely on that repository or related entities for setting up a scenario.
 * The properties below are read in from /test/resources/application.properties, overriding the usual properties file.
 */
@SpringBootTest
public abstract class RepositoryTest {

    @Value("${spring.datasource.driverClassName}")
    String driverClassName;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    JdbcTemplate jdbcTemplate;

    protected DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
