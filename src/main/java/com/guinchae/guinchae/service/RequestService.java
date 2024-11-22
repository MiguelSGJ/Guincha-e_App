package com.guinchae.guinchae.service;

import com.guinchae.guinchae.model.RequestModel;
import com.guinchae.guinchae.model.TowTruckDriverModel;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.model.dto.RequestDto;
import com.guinchae.guinchae.model.type.RequestStatusType;
import com.guinchae.guinchae.repository.RequestRepository;
import com.guinchae.guinchae.repository.TowTruckDriverRepository;
import com.guinchae.guinchae.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TowTruckDriverRepository towTruckDriverRepository;

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

    // TO DO → Metodo para pegar uma Lista de motoristas mais próximos ao solicitante do guincho!, implementar com a api do GOOGLE MAPS
    public List<TowTruckDriverModel> getNearestDrivers(){
        return null;
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

        List<TowTruckDriverModel> nearestDriver = getNearestDrivers();

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

    // Pegar histórico de corridas do usuário!
    public List<RequestModel> getAllRequests() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return requestRepository.findAllByClient(user);
    }
}
