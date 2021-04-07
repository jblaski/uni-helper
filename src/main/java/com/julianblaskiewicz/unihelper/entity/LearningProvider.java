package com.julianblaskiewicz.unihelper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningProvider {
    @Id
    private long UKPRN;
    private String providerName;
    private String viewName;
    private String sortName;
    private String alias;

    private String flatNameNumber;
    private String buildingNameNumber;
    private String locality;
    private String streetName;
    private String town;
    private String postcode;

    private String websiteURL;
    private String wikipediaURL;
    private String groups;

    private Double latitude;
    private Double longitude;

    private int easting;
    private int northing;

    private String GTRid;
    private String HESAid;
}
