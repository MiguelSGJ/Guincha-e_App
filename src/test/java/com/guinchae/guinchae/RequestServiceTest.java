package com.guinchae.guinchae;

import com.guinchae.guinchae.model.LocationModel;
import com.guinchae.guinchae.model.RequestModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.dto.FeatureDto;
import com.guinchae.guinchae.model.dto.MapboxGeocodingResponseDto;
import com.guinchae.guinchae.model.dto.MapboxRouteResponseDto;
import com.guinchae.guinchae.model.dto.RouteDto;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.service.MapboxService;
import com.guinchae.guinchae.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class RequestServiceTest {

    @InjectMocks
    private RequestService requestService; // Objeto principal sendo testado

    @Mock
    private MapboxService mapboxService;

    @Mock
    private TowTruckDriverRepository towTruckDriverRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    void testGetNearestDrivers() {
        // Simular motoristas disponíveis
        TowTruckDriverModel driver1 = new TowTruckDriverModel();
        driver1.setId(1L);
        driver1.setCurrentLocation(new LocationModel(10.0, 10.0));

        TowTruckDriverModel driver2 = new TowTruckDriverModel();
        driver2.setId(2L);
        driver2.setCurrentLocation(new LocationModel(20.0, 20.0));

        when(towTruckDriverRepository.findAvailableDrivers())
                .thenReturn(Arrays.asList(driver1, driver2));

        // Simular geocodificação
        RequestModel request = new RequestModel();
        request.setPickupAddress("Avenida Paulista, São Paulo");

        FeatureDto featureDto = new FeatureDto();
        featureDto.setCoordinates(Arrays.asList(-46.635342, -23.561414)); // [longitude, latitude]
        featureDto.setPlaceName("Avenida Paulista, São Paulo");

        MapboxGeocodingResponseDto geocodingResponse = new MapboxGeocodingResponseDto();
        geocodingResponse.setFeatures(List.of(featureDto));

        when(mapboxService.geocoding("Avenida Paulista, São Paulo"))
                .thenReturn(geocodingResponse);

        // Simular cálculo de distância
        RouteDto routeDto = new RouteDto();
        routeDto.setDistance(500.0);
        routeDto.setDuration(300.0);

        MapboxRouteResponseDto mapboxRouteResponse = new MapboxRouteResponseDto();
        mapboxRouteResponse.setRoutes(List.of(routeDto));

        when(mapboxService.caculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(mapboxRouteResponse);

        // Executar o método
        List<TowTruckDriverModel> nearestDrivers = requestService.getNearestDrivers(request);

        // Verificar o resultado
        assertNotNull(nearestDrivers, "A lista de motoristas não deve ser nula");
        assertEquals(2, nearestDrivers.size(), "Devem ser retornados 2 motoristas");
        assertEquals(1L, nearestDrivers.get(0).getId(), "O motorista mais próximo deve ser o driver1");
    }
}
