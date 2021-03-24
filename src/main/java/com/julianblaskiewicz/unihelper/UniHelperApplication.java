package com.julianblaskiewicz.unihelper;

import com.julianblaskiewicz.unihelper.init.CityDatabaseSetup;
import com.julianblaskiewicz.unihelper.init.LearningProviderDatabaseSetup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.julianblaskiewicz.unihelper.repository.LearningProviderRepository;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class UniHelperApplication {

	@Autowired
	LearningProviderDatabaseSetup learningProviderDatabaseSetup;

	@Autowired
	CityDatabaseSetup cityDatabaseSetup;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(UniHelperApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		if (env.getProperty("initializeDatabases").equals("true")) {
			log.info("initializeDatabase set to TRUE");
			try {
				learningProviderDatabaseSetup.setupDatabaseEntities("data/ukrlp/learning-providers-plus.csv");
			} catch (IOException e) {
				log.error("Error setting up database: {}", e.getMessage());
			}
			try {
				cityDatabaseSetup.setupDatabaseEntities("data/cities/cities.csv");
			} catch (IOException e) {
				log.error("Error setting up database: {}", e.getMessage());
			}
		} else {
			log.info("initializeDatabase set to FALSE");
		}
	}
}
