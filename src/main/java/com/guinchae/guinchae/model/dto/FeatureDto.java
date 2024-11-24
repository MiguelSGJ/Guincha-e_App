package com.guinchae.guinchae.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureDto {

    private String placeName;
    private List<Double> coordinates;
}
