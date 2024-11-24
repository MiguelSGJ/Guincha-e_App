package com.guinchae.guinchae.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RouteDto {

    private String geometry;    // rota otimizada em formato GeoJson
    private double distance;    // distancia da rota
    private double duration;    // duração da rota
}
