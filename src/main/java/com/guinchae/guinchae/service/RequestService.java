package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.RequestModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.model.dto.MapboxGeocodingResponseDto;
import com.guinchae.guinchae.model.dto.RequestDto;
import com.guinchae.guinchae.model.type.RequestStatusType;
import com.guinchae.guinchae.repository.RequestRepository;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;
    private final MapboxService mapboxService;

    public void createRequest(RequestDto requestDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userEmail == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var request = RequestModel.builder()
                .client(userModel)
                .pickupAddress(requestDto.getPickupLocation())
                .dropAddress(requestDto.getDropLocation())
                .message(requestDto.getMessage())
                .status(RequestStatusType.PENDING) // Status inicial
                .build();

        requestRepository.save(request);
    }

    // Metodo para pegar uma Lista de motoristas mais próximos ao solicitante do guincho!, implementar com a api do GOOGLE MAPS
    public List<TowTruckDriverModel> getNearestDrivers(RequestModel request) {
        List<TowTruckDriverModel> availableDrivers = towTruckDriverRepository.findAvailableDrivers();

        // Pega o endereço do request e transforma ele em geolocalização e passa para o request
        MapboxGeocodingResponseDto mapboxGeocode = mapboxService.geocoding(request.getPickupAddress());

        if(mapboxGeocode.getFeatures().isEmpty()){
            throw new RuntimeException("Não foi possível localizar o endereço: " + request.getPickupAddress());
        }

        List<Double> coordinates = mapboxGeocode.getFeatures().get(0).getCoordinates();
        double longitude = coordinates.get(0);
        double latitude = coordinates.get(1);

        // ************************************************************************************

        Map<TowTruckDriverModel, Double> driverDistances = new HashMap<>();
        for (TowTruckDriverModel driver : availableDrivers) {
            var distance = mapboxService.caculateDistance(
                    driver.getCurrentLocation().getLatitude(),
                    driver.getCurrentLocation().getLongitude(),
                    latitude,
                    longitude);

            if (distance.getRoutes() == null || distance.getRoutes().isEmpty()) {
                throw new RuntimeException("Erro ao calcular distancia para o motorista: " + driver.getName());
            }

            driverDistances.put(driver, distance.getRoutes().get(0).getDistance());
        }
        return driverDistances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())   // Ordena os motoristas pela distancia
                .map(Map.Entry::getKey) // Pega os motoristas
                .toList();  // Coloca os motoristas na lista
    }

    // Método que atribui um guincheiro a uma solicitação, e depois espera que o guincheiro aceite ou não a corrida
    public void assignDriverToRequest(Long requestId, List<Long> triedDriverIds) {
        RequestModel request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));

        if (triedDriverIds == null) {
            triedDriverIds = new ArrayList<>();
        }

        if(request.getStatus() == RequestStatusType.ACCEPTED){
            throw new RuntimeException("A solicitação ja foi aceita por um motorista!");
        }

        List<TowTruckDriverModel> nearestDriver = getNearestDrivers(request);

        for (TowTruckDriverModel driver : nearestDriver) {
            if(!triedDriverIds.contains(driver.getId()) && !driver.isAssigned() && driver.isAvailable()){
                request.setTowTruckDriver(driver);
                request.setStatus(RequestStatusType.ASSIGNED);
                driver.setAssigned(true);
                towTruckDriverRepository.save(driver);
                requestRepository.save(request);
                return;
            }
        }

        throw new RuntimeException("Nenhum motorista disponível para a solicitação!");
    }

    public void respondToRequest(Long requestId, boolean driverResponse) {
        RequestModel request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));

        String driverEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (driverEmail == null) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        TowTruckDriverModel towTruckDriver = towTruckDriverRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado!"));

        if(!request.getTowTruckDriver().getId().equals(towTruckDriver.getId())) {
            throw new RuntimeException("Motorista não atribuído a solicitação");
        }

        if(driverResponse){
            request.setStatus(RequestStatusType.ACCEPTED);
            towTruckDriver.setAvailable(false);
        } else {
            towTruckDriver.setAssigned(false);
            request.setTowTruckDriver(null);
            request.setStatus(RequestStatusType.PENDING);

            List<Long> triedDriverIds = request.getTriedDrivers();
            if(triedDriverIds == null){
                triedDriverIds = new ArrayList<>();
            }
            triedDriverIds.add(towTruckDriver.getId());

            assignDriverToRequest(request.getId(), triedDriverIds);
        }

        towTruckDriverRepository.save(towTruckDriver);
        requestRepository.save(request);
    }

    // Pegar histórico de corridas do usuáaio!
    public List<RequestModel> getAllRequests() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return requestRepository.findAllByClient(user);
    }
}
