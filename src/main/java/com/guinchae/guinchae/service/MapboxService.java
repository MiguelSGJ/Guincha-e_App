package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.dto.MapboxGeocodingResponseDto;
import com.guinchae.guinchae.model.dto.MapboxRouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MapboxService {

    @Value("${application.mapbox.api.token}")
    private String apikey;

    private static final String MAPBOX_URL_DISTANCE_CALCULATOR = "https://api.mapbox.com/directions/v5/mapbox/driving";
    private static final String MAPBOX_URL_GEOCODING = "https://api.mapbox.com/geocoding/v5/mapbox.places";

    public MapboxRouteResponseDto caculateDistance(double driverLat, double driverLong, double pickupLat, double pickupLong) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/%f,%f;%f,%f?access_token=%s",
                MAPBOX_URL_DISTANCE_CALCULATOR, driverLat, driverLong, pickupLat, pickupLong, apikey);

        MapboxRouteResponseDto response = restTemplate.getForObject(url, MapboxRouteResponseDto.class);

        if(response == null || response.getRoutes().isEmpty()) {
            throw new RuntimeException("Erro ao calcular distancia");
        }

        return response;
    }

    public MapboxGeocodingResponseDto geocoding(String address) {
        RestTemplate restTemplate = new RestTemplate();

        String encodedAddress = address.replace(" ", "%20");

        String url = String.format("%s/%s.json?access_token=%s&limit=1&autocomplete=true", MAPBOX_URL_GEOCODING, encodedAddress,apikey);

        MapboxGeocodingResponseDto response = restTemplate.getForObject(url, MapboxGeocodingResponseDto.class);

        if(response == null || !response.getFeatures().isEmpty()) {
            throw new RuntimeException("Erro ao obter a geolocalização do endereço" + address);
        }

        return response;
    }
}
